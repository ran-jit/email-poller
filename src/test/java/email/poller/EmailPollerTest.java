package email.poller;

import email.poller.entity.EmailConfig;
import email.poller.impl.EmailPollerImpl;

/**
 * Email Poller Implementation.
 * 
 * Email poller test implementation.
 * 
 * @author Ranjith Manickam
 * @since 1.1
 */
public class EmailPollerTest {

	public static void main(String[] args) {
		EmailPoller emailPoller = null;
		try {

			String protocol = "imaps"; // email server protocol
			String host = "imap.gmail.com"; // email server host
			String lookupFolder = "INBOX"; // lookup folder name
			boolean tlsEnabled = true; // TLS configuration

			String username = "<USERNAME>"; // email user account name
			String password = "<PASSWORD>"; // email user account password

			MessageActionHandlerFactory factory = new MessageActionHandlerFactoryTest();
			EmailConfig config = new EmailConfig(protocol, host, tlsEnabled, username, password, lookupFolder, factory);

			emailPoller = new EmailPollerImpl(config);
			emailPoller.start();

			username = "<USERNAME1>"; // updated email user account name
			password = "<PASSWORD1>"; // updated email user account password

			emailPoller.getConfig().setUsername(username);
			emailPoller.getConfig().setPassword(password);
			emailPoller.getConfig().setPollingTreadSize(2);

			emailPoller.restart();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (emailPoller != null) {
				emailPoller.stop();
			}
		}
	}

}