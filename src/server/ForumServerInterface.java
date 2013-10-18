package server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import topic.TopicInterface;

public interface ForumServerInterface extends Remote {

    /**
     * Get the topic of the discussion identified by the string <i>title</i>.
     * @param title The title of the topic
     * @return An object which implements <i>InterfaceTopic</i> or <i>null</i> if no topic with that title was found.
     *@throws RemoteException
     */
    public TopicInterface getTopic(String title) throws RemoteException;


    /**
     * Get all the topics currently available on the server
     * 
     * @return an collection with all the topics
     */
	public ArrayList<TopicInterface> getAllTopics() throws RemoteException;
	
}
