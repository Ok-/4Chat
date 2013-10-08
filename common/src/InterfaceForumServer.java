import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceForumServer extends Remote {

    /**
     * Get the topic of the discussion identified by the string <i>title</i>.
     * @param title The title of the topic
     * @return An object which implements <i>InterfaceTopic</i> or <i>null</i> if no topic with that title was found.
     *@throws RemoteException
     */
    public InterfaceTopic getTopic(String title) throws RemoteException;

    /**
     * Add a new discussion thread in the list of available topics.
     * @param title The title of the topic
     */
    public InterfaceTopic addTopic(String title) throws RemoteException;

    /**
     * Remove a topic from the discussion threads list.
     */
    public void removeTopic(String title) throws RemoteException;

    /**
     * Get all the topics currently available on the server
     * 
     * @return an collection with all the topics
     */
	public ArrayList<InterfaceTopic> getAllTopics() throws RemoteException;
}
