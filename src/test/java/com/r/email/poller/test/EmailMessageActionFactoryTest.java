package com.r.email.poller.test;

import javax.mail.Message;

import com.r.email.poller.IEmailMessageActionFactory;
import com.r.email.poller.IEmailMessageActionHandler;

/**
 * Email Poller Implementation
 * 
 * Email message action factory implementation example
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailMessageActionFactoryTest implements IEmailMessageActionFactory {

	private IEmailMessageActionHandler emailActionHandler;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object processMessage(Message message) {
		try {

			if (message != null && message.getSubject() != null && message.getSubject().equals("Process message")) {
				emailActionHandler = new EmailMessageActionHandlerTest();
				return message;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEmailMessageActionHandler getActionHandler() {
		return emailActionHandler;
	}

}