package com.r.email.poller;

import javax.mail.Message;

/**
 * Email Poller Implementation
 * 
 * Interface for email message actions
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface IEmailMessageActionFactory {

	/**
	 * To process message
	 * 
	 * @param message
	 * @return the handler input object
	 */
	public Object processMessage(Message message);

	/**
	 * To get email message post action handler
	 * 
	 * @return the email message action handler object
	 */
	public IEmailMessageActionHandler getActionHandler();
}