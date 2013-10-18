package topic;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TopicInterface extends Remote {
	
	/**
     * Add an user in the subscribers list of this topic
     * @param client Client to add to the subscribers list
     * @throws RemoteException
     */
    //public void subscribe(ForumClientInterface client) throws RemoteException;

    /**
     * Remove an user in the subscribers list of this topic
     * @param client Client to remove of the subscribers list
     * @throws RemoteException
     */
    //public void unsubscribe(ForumClientInterface client) throws RemoteException;
    

    /**
     * Send a message to all clients who subscribed to the topic.
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    //public void broadcast(String message) throws RemoteException;
	
	
	/**
	 * Get the title of the topic
	 * @return a string containing the title of the topic
	 * @throws RemoteException
	 */
	public String getTopicTitle() throws RemoteException;
}
