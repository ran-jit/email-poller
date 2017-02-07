package com.r.email.poller;

/**
 * Email Poller Implementation
 * 
 * Interface for email poller operations
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface IEmailPoller {

	/**
	 * To start the email polling operation
	 */
	public void start();

	/**
	 * To stop the email polling operation
	 */
	public void stop();

	/**
	 * To restart the email polling operation
	 */
	public void restart();
}