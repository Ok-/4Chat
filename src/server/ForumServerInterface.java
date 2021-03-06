package server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import exceptions.ServerOverloadedException;
import exceptions.UniqueTitleViolationException;
import topic.TopicInterface;

public interface ForumServerInterface extends Remote {
	
	/**
	 * Submit the client's pseudo in the server's client list
	 * @param pseudo Pseudo of the client who connected
	 * @throws RemoteException
	 */
	public void connect(String pseudo) throws RemoteException;
	
	
	/**
	 * Remove the pseudo from the client list
	 * @param pseudo Pseudo of the client who disconnected
	 * @throws RemoteException
	 */
	public void disconnect(String pseudo) throws RemoteException;
	
	
	/**
	 * Check if a pseudo is already picked or not
	 * @param pseudo Pseudo we want to check for availability
	 * @return true if pseudo is available, false if pseudo is already picked
	 * @throws RemoteException
	 */
	public boolean isPseudoAvailable(String pseudo) throws RemoteException;
	
	/**
	 * Check if the topic we are going to create doesn't exists yet
	 * @param topicTitle Title we want to check
	 * @return true if the title is available, false if not
	 * @throws RemoteException
	 */
	public boolean isTopicTitleAvailable(String topicTitle) throws RemoteException;
	

    /**
     * Get the topic of the discussion identified by the string <i>title</i>.
     * @param title The title of the topic
     * @return An object which implements <i>InterfaceTopic</i> or <i>null</i> if no topic with that title was found.
     * @throws RemoteException
     */
    public TopicInterface getTopic(String title) throws RemoteException;


    /**
     * Get all the topics currently available on the server
     * @return a collection with all the topics
     * @throws RemoteException
     */
	public ArrayList<TopicInterface> getAllTopics() throws RemoteException;


	/**
	 * Create a new topic that will be hosted by ForumServer object
	 * @param title
	 * @throws RemoteException
	 * @throws UniqueTitleViolationException 
	 * @throws ServerOverloadedException 
	 */
	public void createTopic(String topicTitle) throws RemoteException, UniqueTitleViolationException, ServerOverloadedException;
	
}
