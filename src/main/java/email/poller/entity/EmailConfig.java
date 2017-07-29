package email.poller.entity;

import email.poller.MessageActionHandlerFactory;
import email.poller.constants.EmailPollerConstants;

/**
 * Email Poller Implementation.
 * 
 * Email polling configuration.
 * 
 * @author Ranjith Manickam
 * @since 1.1
 */
public class EmailConfig {

	private int port;
	private String host;
	private String protocol;
	private String username;
	private String password;
	private int pollingDelay;
	private String lookupFolder;
	private boolean tlsEnabled;
	private int pollingTreadSize;

	private MessageActionHandlerFactory factory;

	/**
	 * @param protocol
	 * @param host
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, String username, String password, String lookupFolder,
			MessageActionHandlerFactory factory) {
		this(protocol, host, false, username, password, lookupFolder, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param pollingTreadSize
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, String username, String password, String lookupFolder,
			int pollingTreadSize, MessageActionHandlerFactory factory) {
		this(protocol, host, false, username, password, lookupFolder, pollingTreadSize, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, String username, String password, String lookupFolder,
			MessageActionHandlerFactory factory) {
		this(protocol, host, port, false, username, password, lookupFolder, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param pollingTreadSize
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, String username, String password, String lookupFolder,
			int pollingTreadSize, MessageActionHandlerFactory factory) {
		this(protocol, host, port, false, username, password, lookupFolder, pollingTreadSize, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, boolean tlsEnabled, String username, String password,
			String lookupFolder, MessageActionHandlerFactory factory) {
		this(protocol, host, 0, tlsEnabled, username, password, lookupFolder, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param pollingTreadSize
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, boolean tlsEnabled, String username, String password,
			String lookupFolder, int pollingTreadSize, MessageActionHandlerFactory factory) {
		this(protocol, host, 0, tlsEnabled, username, password, lookupFolder, pollingTreadSize, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, boolean tlsEnabled, String username, String password,
			String lookupFolder, MessageActionHandlerFactory factory) {
		this(protocol, host, port, tlsEnabled, username, password, 0, lookupFolder, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param lookupFolder
	 * @param pollingTreadSize
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, boolean tlsEnabled, String username, String password,
			String lookupFolder, int pollingTreadSize, MessageActionHandlerFactory factory) {
		this(protocol, host, port, tlsEnabled, username, password, 0, lookupFolder, pollingTreadSize, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param pollingDelay
	 * @param lookupFolder
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, boolean tlsEnabled, String username, String password,
			int pollingDelay, String lookupFolder, MessageActionHandlerFactory factory) {
		this(protocol, host, port, tlsEnabled, username, password, pollingDelay, lookupFolder, 0, factory);
	}

	/**
	 * @param protocol
	 * @param host
	 * @param port
	 * @param tlsEnabled
	 * @param username
	 * @param password
	 * @param pollingDelay
	 * @param lookupFolder
	 * @param pollingTreadSize
	 * @param factory
	 */
	public EmailConfig(String protocol, String host, int port, boolean tlsEnabled, String username, String password,
			int pollingDelay, String lookupFolder, int pollingTreadSize, MessageActionHandlerFactory factory) {

		setHost(host);
		setProtocol(protocol);
		setUsername(username);
		setPassword(password);
		setLookupFolder(lookupFolder);
		setTlsEnabled(tlsEnabled);
		setFactory(factory);

		setPort(port);
		setPollingDelay(pollingDelay);
	}

	/**
	 * To get email server port
	 * 
	 * @return
	 */
	public int getPort() {
		return (port <= 0 && EmailPollerConstants.DEFAULT_MAIL_SERVER_PORTS.containsKey(this.protocol))
				? EmailPollerConstants.DEFAULT_MAIL_SERVER_PORTS.get(this.protocol)
				: port;
	}

	/**
	 * To set email server port
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * To get email server host
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * To set email server host
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * To get email server protocol
	 * 
	 * @return
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * To set email server protocol
	 * 
	 * @param protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * To get email server user-name
	 * 
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * To set email server user-name
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * To get email server password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * To set email server password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * To get email message polling delay
	 * 
	 * @return
	 */
	public int getPollingDelay() {
		return (pollingDelay <= 0) ? EmailPollerConstants.DEFAULT_POLLING_DELAY : pollingDelay;
	}

	/**
	 * To set email message polling delay
	 * 
	 * @param pollingDelay
	 */
	public void setPollingDelay(int pollingDelay) {
		this.pollingDelay = pollingDelay;
	}

	/**
	 * To get email message lookup folder
	 * 
	 * @return
	 */
	public String getLookupFolder() {
		return lookupFolder;
	}

	/**
	 * To set email message lookup folder
	 * 
	 * @param lookupFolder
	 */
	public void setLookupFolder(String lookupFolder) {
		this.lookupFolder = lookupFolder;
	}

	/**
	 * To get email server tls enabled
	 * 
	 * @return
	 */
	public boolean isTlsEnabled() {
		return tlsEnabled;
	}

	/**
	 * To set email server tls enabled
	 * 
	 * @param tlsEnabled
	 */
	public void setTlsEnabled(boolean tlsEnabled) {
		this.tlsEnabled = tlsEnabled;
	}

	/**
	 * To get email message polling thread size
	 * 
	 * @return
	 */
	public int getPollingTreadSize() {
		return (pollingTreadSize <= 0) ? EmailPollerConstants.DEFAULT_POLLING_THREAD_SIZE : pollingTreadSize;
	}

	/**
	 * To set email message polling thread size
	 * 
	 * @param pollingTreadSize
	 */
	public void setPollingTreadSize(int pollingTreadSize) {
		this.pollingTreadSize = pollingTreadSize;
	}

	/**
	 * To get email message action handler factory
	 * 
	 * @return
	 */
	public MessageActionHandlerFactory getFactory() {
		return factory;
	}

	/**
	 * To set email message action handler factory
	 * 
	 * @param factory
	 */
	public void setFactory(MessageActionHandlerFactory factory) {
		this.factory = factory;
	}

}