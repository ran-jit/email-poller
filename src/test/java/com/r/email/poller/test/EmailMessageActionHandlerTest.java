package com.r.email.poller.test;

import javax.mail.Message;

import com.r.email.poller.IEmailMessageActionHandler;
import com.r.email.poller.util.EmailMessageUtil;

/**
 * Email Poller Implementation
 * 
 * Email message action handler implementation example
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailMessageActionHandlerTest implements IEmailMessageActionHandler {

	private Message message;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Object obj) {
		message = (Message) obj;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasAccess() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doAction() {
		try {
			String content = EmailMessageUtil.getMessageContent(message);
			System.out.println("Email message content: " + content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}