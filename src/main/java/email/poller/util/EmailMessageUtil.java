package email.poller.util;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

/**
 * Email Poller Implementation
 * 
 * Email message utilities
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public class EmailMessageUtil {

	/**
	 * method to get email message content
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String getMessageContent(Message message) throws Exception {
		String content = null;
		if (message.getContent() instanceof MimeMultipart) {
			StringBuilder emailContent = new StringBuilder();
			parseMultipart((MimeMultipart) message.getContent(), emailContent);

			content = emailContent.toString();
		} else {
			content = String.valueOf(message.getContent());
		}

		// pattern to replace non ASCII characters from email body content
		content = content.replaceAll("[^\\x00-\\x7F]", "");

		return content;
	}

	/**
	 * method to parse multi-part contents
	 * 
	 * @param mimeMultipart
	 * @param emailContent
	 * @throws MessagingException
	 * @throws IOException
	 */
	private static void parseMultipart(MimeMultipart mimeMultipart, StringBuilder emailContent)
			throws MessagingException, IOException {
		for (int i = 0; i < mimeMultipart.getCount(); i++) {
			BodyPart part = mimeMultipart.getBodyPart(i);
			if (part.getContent() instanceof MimeMultipart) {
				parseMultipart((MimeMultipart) part.getContent(), emailContent);
			}

			if (part.getContentType().toLowerCase().contains("text/plain")) {
				emailContent.append(part.getContent());
			}
		}
	}
}