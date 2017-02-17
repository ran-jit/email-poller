package com.r.email.poller.test;

import com.r.email.poller.IEmailMessageActionFactory;
import com.r.email.poller.IEmailPoller;
import com.r.email.poller.impl.EmailPoller;

/**
 * Email Poller Implementation
 * 
 * test example 2
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailPollerTest2 {

	public static void main(String[] args) {

		IEmailPoller emailPoller = null;
		try {
			String protocol = "imaps"; // email server protocol
			String host = "imap.gmail.com"; // email server host
			String username = "<USERNAME>"; // email user account name
			String password = "<PASSWORD>"; // email user account password
			String lookupFolder = "INBOX"; // lookup folder name
			Boolean tlsEnabled = Boolean.TRUE; // TLS configuration

			IEmailMessageActionFactory emailActionFactory = new EmailMessageActionFactoryTest();
			emailPoller = new EmailPoller(protocol, host, tlsEnabled, username, password, lookupFolder,
					emailActionFactory);

			// starting email polling process
			emailPoller.start();

			Thread.sleep(50000);

			username = "<USERNAME1>";
			password = "<PASSWORD1>";

			emailPoller.setUsername(username);
			emailPoller.setPassword(password);

			// restarting email polling process
			emailPoller.restart();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (emailPoller != null)
				emailPoller.start();
		}
	}
}