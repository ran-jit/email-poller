package email.poller;

/**
 * Email Poller Implementation.
 * 
 * Interface to handle email message action.
 * 
 * @author Ranjith Manickam
 * @version 1.0
 */
public interface MessageActionHandler {

	/**
	 * To initialize handler
	 * 
	 * @param obj
	 */
	void init(Object obj);

	/**
	 * Post action
	 */
	void doAction();
}