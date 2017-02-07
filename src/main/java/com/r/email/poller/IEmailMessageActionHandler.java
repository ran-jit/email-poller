package com.r.email.poller;

/**
 * Email Poller Implementation
 * 
 * Interface for email message action handler
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface IEmailMessageActionHandler {

	/**
	 * To initialize handler
	 * 
	 * @param obj (email action factory process message returned object)
	 */
	public void init(Object obj);

	/**
	 * To check the access
	 * 
	 * @return If true the process move to next level
	 */
	public boolean hasAccess();

	/**
	 * Post action
	 */
	public void doAction();
}