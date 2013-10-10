import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface InterfaceTopic extends Remote {

    /**
     * Add an user in the subscribers list of this topic
     * @param client Client to add to the subscribers list
     * @throws RemoteException
     */
    public void subscribe(InterfaceForumClient client) throws RemoteException;

    /**
     * Remove an user in the subscribers list of this topic
     * @param client Client to remove of the subscribers list
     * @throws RemoteException
     */
    public void unsubscribe(InterfaceForumClient client) throws RemoteException;

    /**
     * Send a message to all clients who subscribed to the topic.
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    public void broadcast(String message) throws RemoteException;

    /**
     * Getter on the topic
     * @return the topic of the discussion
     */
    public String getTopic() throws RemoteException;
}
