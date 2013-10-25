package provider;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import exceptions.UniqueTitleViolationException;
import topic.TopicInterface;

public interface HosterInterface extends Remote {
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
	 * Create a new topic that will be hosted by Hoster object
	 * @param title
	 * @throws RemoteException
	 * @throws UniqueTitleViolationException
	 */
	public void createHostedTopic(String topicTitle) throws RemoteException, UniqueTitleViolationException;

	
	/**
	 * Get the server load (topics hosted / capacity)
	 * @return an integer between 0 and a 100. 0 : server has no topics. 100 : server is full
	 * @throws RemoteException
	 */
	public int getServerLoad() throws RemoteException;
}
