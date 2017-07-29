package email.poller;

import email.poller.entity.EmailConfig;

/**
 * Email Poller Implementation.
 * 
 * Interface for email message polling.
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface EmailPoller {

	/**
	 * To start the email poller
	 */
	void start();

	/**
	 * To stop the email poller
	 */
	void stop();

	/**
	 * To restart the email poller
	 */
	void restart();

	/**
	 * To get email configuration
	 * 
	 * @return
	 */
	EmailConfig getConfig();
}