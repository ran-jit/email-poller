package com.r.email.poller.test;

import com.r.email.poller.IEmailMessageActionFactory;
import com.r.email.poller.IEmailPoller;
import com.r.email.poller.impl.EmailPoller;

/**
 * Email Poller Implementation
 * 
 * test example 1
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailPollerTest1 {

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
		} finally {
			if (emailPoller != null)
				emailPoller.start();
		}
	}
}