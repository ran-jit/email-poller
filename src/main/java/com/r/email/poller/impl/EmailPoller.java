package com.r.email.poller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.r.email.poller.IEmailMessageActionFactory;
import com.r.email.poller.IEmailMessageActionHandler;
import com.r.email.poller.IEmailPoller;
import com.r.email.poller.constants.EmailPollerConstants;

/**
 * Email Poller Implementation
 * 
 * This class uses to read and process email messages
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailPoller implements IEmailPoller, Runnable {

	private int port;
	private int consumerDelay;

	private String protocol;
	private String host;
	private String username;
	private String password;
	private String lookupFolder;
	private Boolean tlsEnabled = Boolean.FALSE;
	private Boolean processEmailPolling = Boolean.TRUE;
	private Boolean restartEmailPolling = Boolean.FALSE;

	private IEmailMessageActionFactory emailActionFactory;

	private final List<Integer> processMessageIds = new ArrayList<>();

	private final Log log = LogFactory.getLog(EmailPoller.class);

	/**
	 * Default constructor
	 */
	private EmailPoller() {
	}

	/**
	 * Email poller constructor
	 * 
	 * @param protocol
	 * @param host
	 * @param port (protocol default port)
	 * @param tlsEnabled (default value is false)
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay (default value 2mins)
	 * @param emailActionFactory
	 */
	public EmailPoller(String protocol, String host, String username, String password, String lookupFolder,
			IEmailMessageActionFactory emailActionFactory) {
		initialize(protocol, host, 0, Boolean.FALSE, username, password, lookupFolder, 0, emailActionFactory);
	}

	/**
	 * Email poller constructor
	 * 
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled (default value is false)
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay (default value 2mins)
	 * @param emailActionFactory
	 */
	public EmailPoller(String protocol, String host, int port, String username, String password, String lookupFolder,
			IEmailMessageActionFactory emailActionFactory) {
		initialize(protocol, host, port, Boolean.FALSE, username, password, lookupFolder, 0, emailActionFactory);
	}

	/**
	 * Email poller constructor
	 * 
	 * @param protocol
	 * @param host
	 * @param port (protocol default port)
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay (default value 2mins)
	 * @param emailActionFactory
	 */
	public EmailPoller(String protocol, String host, Boolean tlsEnabled, String username, String password,
			String lookupFolder, IEmailMessageActionFactory emailActionFactory) {
		initialize(protocol, host, 0, tlsEnabled, username, password, lookupFolder, 0, emailActionFactory);
	}

	/**
	 * Email poller constructor
	 * 
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay (default value 2mins)
	 * @param emailActionFactory
	 */
	public EmailPoller(String protocol, String host, int port, Boolean tlsEnabled, String username, String password,
			String lookupFolder, IEmailMessageActionFactory emailActionFactory) {
		initialize(protocol, host, port, tlsEnabled, username, password, lookupFolder, 0, emailActionFactory);
	}

	/**
	 * Email poller constructor
	 * 
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay
	 * @param emailActionFactory
	 */
	public EmailPoller(String protocol, String host, int port, Boolean tlsEnabled, String username, String password,
			String lookupFolder, int consumerDelay, IEmailMessageActionFactory emailActionFactory) {
		initialize(protocol, host, port, tlsEnabled, username, password, lookupFolder, consumerDelay,
				emailActionFactory);
	}

	@Override
	public void run() {
		startProcess();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() {
		processEmailPolling = Boolean.FALSE;
		restartEmailPolling = Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void restart() {
		processEmailPolling = Boolean.FALSE;
		restartEmailPolling = Boolean.TRUE;
	}

	/**
	 * method to initialize the required properties
	 * 
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param consumerDelay
	 * @param emailActionFactory
	 */
	private void initialize(String protocol, String host, int port, Boolean tlsEnabled, String username,
			String password, String lookupFolder, int consumerDelay, IEmailMessageActionFactory emailActionFactory) {
		this.protocol = protocol;
		this.host = host;
		this.port = getEmailPort(protocol, port);
		this.tlsEnabled = tlsEnabled;
		this.username = username;
		this.password = password;
		this.lookupFolder = lookupFolder;
		this.consumerDelay = getConsumerDelay(consumerDelay);
		this.emailActionFactory = emailActionFactory;
	}

	/**
	 * method to get email server default port based on the protocol
	 * 
	 * @param protocol
	 * @param port
	 * @return
	 */
	private int getEmailPort(String protocol, int port) {
		return (port <= 0 && EmailPollerConstants.MAIL_SERVER_PORTS.containsKey(protocol))
				? EmailPollerConstants.MAIL_SERVER_PORTS.get(protocol) : port;
	}

	private int getConsumerDelay(int consumerDelay) {
		return (consumerDelay > 0) ? consumerDelay : EmailPollerConstants.CONSUMER_DELAY;
	}

	/**
	 * method to start the email polling process
	 */
	private void startProcess() {
		try {
			do {
				processMessageIds.clear();

				startEmailPolling();
				Thread.sleep(consumerDelay);

			} while (processEmailPolling);

			if (restartEmailPolling) {
				processEmailPolling = Boolean.TRUE;
				restartEmailPolling = Boolean.FALSE;
				startProcess();
			}
		} catch (Exception ex) {
			log.error("Error occured in email polling job", ex);
		}
	}

	/**
	 * method to start email polling process
	 * 
	 * @throws Exception
	 */
	private void startEmailPolling() throws Exception {
		Store store = null;
		Folder folder = null;
		try {
			Properties properties = new Properties();
			properties.put(EmailPollerConstants.HOSTNAME, host);
			properties.put(EmailPollerConstants.PORT, port);
			properties.put(EmailPollerConstants.TLS_ENABLED, tlsEnabled);

			// create session
			Session session = Session.getDefaultInstance(properties);
			store = session.getStore(protocol);

			// authenticate
			store.connect(host, username, password);

			// create the lookup folder and open with read and write access
			folder = store.getFolder(lookupFolder);
			folder.open(Folder.READ_WRITE);

			// search term to retrieve unseen messages from the folder
			FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flag.SEEN), false);
			Message[] messages = folder.search(unseenFlagTerm);

			if (messages != null && messages.length > 0L) {
				for (Message message : messages) {

					boolean isPrcess = false;
					// check the message is processing
					if (!processMessageIds.contains(message.getMessageNumber())) {
						processMessageIds.add(message.getMessageNumber());
						isPrcess = true;
					}

					if (isPrcess) {
						// double check the message is unseen
						Message[] processMessage = folder.search(unseenFlagTerm, new Message[] { message });
						if (processMessage != null && processMessage.length > 0L) {

							// process message
							processMessage(message);

							// update message seen flag
							message.setFlag(Flag.SEEN, true);

						}

						// removing the processed message
						processMessageIds.remove(message.getMessageNumber());
					}
				}
			}

		} finally {
			closeResources(store, folder);
		}
	}

	/**
	 * method to process message
	 * 
	 * @param message
	 * @throws Exception
	 */
	private void processMessage(Message message) throws Exception {
		Object obj = emailActionFactory.processMessage(message);
		IEmailMessageActionHandler emailActionHandler = emailActionFactory.getActionHandler();

		emailActionHandler.init(obj);
		if (emailActionHandler.hasAccess())
			emailActionHandler.doAction();
	}

	/**
	 * method to close email server resources
	 * 
	 * @param store
	 * @param folder
	 * @throws MessagingException
	 */
	private void closeResources(Store store, Folder folder) throws MessagingException {
		if (folder != null)
			folder.close(false);

		if (store != null)
			store.close();
	}
}