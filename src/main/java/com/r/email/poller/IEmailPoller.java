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

	/**
	 * Sets the email server port
	 * 
	 * @param port
	 */
	public void setPort(int port);

	/**
	 * Sets the email poller message reading delay
	 * 
	 * @param consumerDelay
	 */
	public void setConsumerDelay(int consumerDelay);

	/**
	 * Sets the email server protocol
	 * 
	 * @param protocol
	 */
	public void setProtocol(String protocol);

	/**
	 * Sets the email server host
	 * 
	 * @param host
	 */
	public void setHost(String host);

	/**
	 * Sets the email account user name
	 * 
	 * @param username
	 */
	public void setUsername(String username);

	/**
	 * Sets the email account password
	 * 
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * Sets the email message lookup folder
	 * 
	 * @param lookupFolder
	 */
	public void setLookupFolder(String lookupFolder);

	/**
	 * Sets the email server tls configuration
	 * 
	 * @param tlsEnabled
	 */
	public void setTlsEnabled(Boolean tlsEnabled);

	/**
	 * Sets the email message action factory
	 * 
	 * @param emailActionFactory
	 */
	public void setEmailActionFactory(IEmailMessageActionFactory emailActionFactory);

}