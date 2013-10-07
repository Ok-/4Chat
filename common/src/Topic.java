import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Topic implements InterfaceTopic, Serializable {

    private String topic;
    private LinkedHashSet<InterfaceForumClient> subscribers;

    /**
     * Constructor
     * @param topic Subject of the discussion
     */
    public Topic(String topic) {
        this.topic = topic;
        this.subscribers = new LinkedHashSet<InterfaceForumClient>();
    }

    /**
     * Add an user in the subscribers list of this topic
     * @param client Client to add to the subscribers list
     * @throws RemoteException
     */
    @Override
    public void subscribe(InterfaceForumClient client) throws RemoteException {
        this.subscribers.add(client);
    }

    /**
     * Remove an user in the subscribers list of this topic
     * @param client Client to remove of the subscribers list
     * @throws RemoteException
     */
    @Override
    public void unsubscribe(InterfaceForumClient client) throws RemoteException {
        this.subscribers.remove(client);
    }

    /**
     * Send a message to all clients who subscribed to the topic.
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    @Override
    public void broadcast(String message) throws RemoteException {
        Iterator<InterfaceForumClient> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
            InterfaceForumClient client = iterator.next();
            client.display(message);
        }
    }

    /**
     * Getter on the topic
     * @return the topic of the discussion
     */
    @Override
    public String getTopic() {
        return this.topic;
    }
}
