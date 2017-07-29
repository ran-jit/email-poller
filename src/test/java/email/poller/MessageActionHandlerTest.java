package email.poller;

import javax.mail.Message;

import email.poller.util.EmailMessageUtil;

/**
 * Email Poller Implementation.
 * 
 * Email message action handler test implementation.
 * 
 * @author Ranjith Manickam
 * @since 1.1
 */
public class MessageActionHandlerTest implements MessageActionHandler {

	private Message message;

	/** {@inheritDoc} */
	@Override
	public void init(Object obj) {
		this.message = (Message) obj;
	}

	/** {@inheritDoc} */
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