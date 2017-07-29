package email.poller.constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Email Poller Implementation.
 * 
 * Email poller Constants.
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailPollerConstants {

	public static final String HOST = "mail.host";
	public static final String PORT = "mail.port";
	public static final String TLS_ENABLED = "mail.starttls.enable";

	public static final int DEFAULT_POLLING_DELAY = 120000;
	public static final int DEFAULT_POLLING_THREAD_SIZE = 1;

	public static Map<String, Integer> DEFAULT_MAIL_SERVER_PORTS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	static {
		DEFAULT_MAIL_SERVER_PORTS.put("pop3", 110);
		DEFAULT_MAIL_SERVER_PORTS.put("pop3s", 995);

		DEFAULT_MAIL_SERVER_PORTS.put("imap", 143);
		DEFAULT_MAIL_SERVER_PORTS.put("imaps", 143);

		DEFAULT_MAIL_SERVER_PORTS.put("smtp", 143);
		DEFAULT_MAIL_SERVER_PORTS.put("smtps", 465);
	}

	public static final String MESSAGE_EXEC_FAILED_RETRY_MSG = "Message execution failed, retrying...";
}