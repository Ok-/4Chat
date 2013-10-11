import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ForumServer extends UnicastRemoteObject implements InterfaceForumServer, Serializable {

    private HashMap<String, InterfaceTopic> topics;

    public ForumServer() throws RemoteException {
        super();
        this.topics = new HashMap<String, InterfaceTopic>();
    }

    /**
     *
     * @param title The title of the topic
     * @return An object which implements <i>InterfaceTopic</i> or <i>null</i> if no topic with that title was found.
     * @throws RemoteException
     */
    @Override
    public InterfaceTopic getTopic(String title) throws RemoteException {
        InterfaceTopic topic = this.topics.get(title);
        if (null == topic) {
            topic = this.addTopic(title);
        }
        return topic;
    }

	@Override
	public ArrayList<InterfaceTopic> getAllTopics() throws RemoteException {
		return new ArrayList<InterfaceTopic>(this.topics.values());
	}

    /**
     * Add a new discussion thread in the list of available topics. If the discussion thread already exists in the list, it does nothing and if it doesn't exist, it will be created.
     * @param title The title of the topic
     * @return The topic which has been added
     * @throws RemoteException
     */
    @Override
    public InterfaceTopic addTopic(String title) throws RemoteException{
        InterfaceTopic topic = topics.get(title);
        if (null == topic) {
            topic = new Topic(title);
            topics.put(title, topic);
        }
        return topic;
    }

    /**
     * Remove a topic from the discussion threads list.
      * @param title The title of the topic
     * @throws RemoteException
     */
    @Override
    public void removeTopic(String title) throws RemoteException {
        topics.remove(title);
    }
    
    public void disconnect(InterfaceForumClient client) throws RemoteException {
    	Iterator it = this.topics.values().iterator();
		while(it.hasNext()) {
			Topic currentTopic = (Topic) it.next();
			currentTopic.unsubscribe(client);
		}
    }
    
    
    /**
     * Check if pseudo is available
     * 
     * @return a boolean. True if it's ok.
     */
	public boolean isPseudoAvailable(String pseudo) throws RemoteException {
		
		System.out.println("Checking pseudo "+ pseudo + ". People connected :");
		
		boolean isAvailable = true;
		
		Iterator it = this.topics.values().iterator();
		while(it.hasNext() & isAvailable) {
			Topic currentTopic = (Topic) it.next();
			
			Iterator it2 = currentTopic.getAllSubscribers().iterator();
			while(it2.hasNext() && isAvailable) {
				String toCompare = ((InterfaceForumClient) it2.next()).getPseudo();
				System.out.println("\t"+toCompare);
				
				if (pseudo.equals(toCompare)) {
					System.out.println("\t\t--> FOUND");
					isAvailable = false;
				}
			}
		}
		
		return isAvailable;
	}
    
    
    
}
