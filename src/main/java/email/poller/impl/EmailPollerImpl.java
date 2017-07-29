package email.poller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import email.poller.EmailPoller;
import email.poller.MessageActionHandler;
import email.poller.constants.EmailPollerConstants;
import email.poller.entity.EmailConfig;

/**
 * Email Poller Implementation.
 * 
 * @author Ranjith Manickam
 * @since 1.1
 */
public class EmailPollerImpl implements EmailPoller {

	private EmailConfig config;

	private ExecutorService threadPool;
	private List<Runnable> pollingThreads;

	private final List<Integer> processMessageIds;

	private Log log = LogFactory.getLog(EmailPollerImpl.class);

	/**
	 * @param config
	 */
	public EmailPollerImpl(EmailConfig config) {
		this.config = config;
		this.processMessageIds = new ArrayList<>();
		this.threadPool = Executors.newFixedThreadPool(config.getPollingTreadSize());
	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		Runnable worker = new EmailPollerThread(this.config, this.processMessageIds);
		this.pollingThreads = (this.pollingThreads == null) ? new ArrayList<Runnable>() : this.pollingThreads;
		this.pollingThreads.add(worker);
		this.threadPool.execute(worker);
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		if (this.pollingThreads != null && !this.pollingThreads.isEmpty()) {
			for (Runnable pollingThread : this.pollingThreads) {
				((EmailPollerThread) pollingThread).stop();
			}
		}
		if (this.threadPool != null && !this.threadPool.isTerminated()) {
			this.threadPool.shutdown();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void restart() {
		this.stop();
		this.start();
	}

	/** {@inheritDoc} */
	@Override
	public EmailConfig getConfig() {
		return this.config;
	}

	/**
	 * Email Poller Implementation.
	 * 
	 * Email poller thread for processing email messages.
	 * 
	 * @author Ranjith Manickam
	 * @since 1.1
	 */
	private class EmailPollerThread implements Runnable {

		private EmailConfig config;

		private boolean isAlive = true;
		private final int numRetries = 3;
		private final List<Integer> processMessageIds;

		/**
		 * @param config
		 * @param processMessageIds
		 */
		public EmailPollerThread(EmailConfig config, final List<Integer> processMessageIds) {
			this.config = config;
			this.processMessageIds = processMessageIds;
		}

		/** {@inheritDoc} */
		@Override
		public void run() {
			while (this.isAlive) {
				try {
					startEmailPolling();
					Thread.sleep(this.config.getPollingDelay());
				} catch (Exception ex) {
					// suppress
				}
			}
		}

		/**
		 * To start email polling
		 * 
		 * @throws Exception
		 */
		private void startEmailPolling() throws Exception {
			Store store = null;
			Folder folder = null;
			try {
				Properties properties = new Properties();
				properties.put(EmailPollerConstants.HOST, this.config.getHost());
				properties.put(EmailPollerConstants.PORT, this.config.getPort());
				properties.put(EmailPollerConstants.TLS_ENABLED, this.config.isTlsEnabled());

				// create session
				Session session = Session.getDefaultInstance(properties);
				store = session.getStore(this.config.getProtocol());

				// authenticate
				store.connect(this.config.getHost(), this.config.getUsername(), this.config.getPassword());

				// create the lookup folder and open with read and write access
				folder = store.getFolder(this.config.getLookupFolder());
				folder.open(Folder.READ_WRITE);

				// search term to retrieve unseen messages from the folder
				FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flag.SEEN), false);
				Message[] messages = folder.search(unseenFlagTerm);

				if (messages != null && messages.length > 0L) {
					for (Message message : messages) {

						// check the message is processing
						if (!this.processMessageIds.contains(message.getMessageNumber())) {
							this.processMessageIds.add(message.getMessageNumber());

							// double check the message is unseen
							Message[] processMessage = folder.search(unseenFlagTerm, new Message[] { message });
							if (processMessage != null && processMessage.length > 0L) {

								// process message
								boolean processed = processMessage(message);

								if (processed) {
									// update message seen flag
									message.setFlag(Flag.SEEN, true);
								}
							}

							// removing the processed message
							this.processMessageIds.remove(message.getMessageNumber());
						}
					}
				}

			} finally {
				if (folder != null)
					folder.close(false);

				if (store != null)
					store.close();
			}
		}

		/**
		 * To process email message
		 * 
		 * @param message
		 * @return
		 * @throws Exception
		 */
		private boolean processMessage(Message message) throws Exception {
			int tries = 0;
			boolean sucess = false;
			do {
				if (!this.isAlive) {
					break;
				}

				tries++;
				try {
					MessageActionHandler emailActionHandler = config.getFactory().getMessageActionHandler(message);

					emailActionHandler.init(message);
					emailActionHandler.doAction();

					sucess = true;
				} catch (Exception ex) {
					log.error(EmailPollerConstants.MESSAGE_EXEC_FAILED_RETRY_MSG + tries);
				}
			} while (!sucess && tries <= numRetries);
			return sucess;
		}

		/**
		 * To stop the thread execution
		 */
		public void stop() {
			this.isAlive = false;
		}
	}
}