package email.poller;

import javax.mail.Message;

/**
 * Email Poller Implementation.
 * 
 * Interface to get email message action handler.
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface MessageActionHandlerFactory {

	/**
	 * To get message action handler
	 * 
	 * @param message
	 * @return
	 */
	MessageActionHandler getMessageActionHandler(Message message);

}