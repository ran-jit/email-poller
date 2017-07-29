package email.poller;

import javax.mail.Message;

/**
 * Email Poller Implementation.
 * 
 * Email message action handler factory test implementation.
 * 
 * @author Ranjith Manickam
 * @since 1.1
 */
public class MessageActionHandlerFactoryTest implements MessageActionHandlerFactory {

	/** {@inheritDoc} */
	@Override
	public MessageActionHandler getMessageActionHandler(Message message) {
		try {
			if (message != null && message.getSubject() != null && message.getSubject().equals("Process message")) {
				return new MessageActionHandlerTest();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}