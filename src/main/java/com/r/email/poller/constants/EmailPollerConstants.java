package com.r.email.poller.constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Email Poller Implementation
 * 
 * Constants for email polling
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailPollerConstants {

	public static final String HOSTNAME = "mail.host";

	public static final String PORT = "mail.port";

	public static final String TLS_ENABLED = "mail.starttls.enable";

	public static final int CONSUMER_DELAY = 120000;

	public static Map<String, Integer> MAIL_SERVER_PORTS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	static {
		MAIL_SERVER_PORTS.put("pop3", 110);
		MAIL_SERVER_PORTS.put("pop3s", 995);

		MAIL_SERVER_PORTS.put("imap", 143);
		MAIL_SERVER_PORTS.put("imaps", 143);

		MAIL_SERVER_PORTS.put("smtp", 143);
		MAIL_SERVER_PORTS.put("smtps", 465);
	}
}