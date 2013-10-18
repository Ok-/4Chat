package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import topic.Topic;
import topic.TopicInterface;

public class ForumServer extends UnicastRemoteObject implements ForumServerInterface, Serializable {

	private static final long serialVersionUID = 1L;
	
	private HashMap<String, TopicInterface> topics;
	private LinkedList<String> clientPseudos;
	
	

    public ForumServer() throws RemoteException {
        super();
        this.topics = new HashMap<String, TopicInterface>();
        this.clientPseudos = new LinkedList<String>();
    }

    
    /**
     * Interface methods
     */
	public TopicInterface getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}

	public ArrayList<TopicInterface> getAllTopics() throws RemoteException {
		return new ArrayList<TopicInterface>(this.topics.values());
	}

	public void createLocalTopic(String title) throws RemoteException {
		TopicInterface topic = new Topic(title);
        topics.put(title, topic);
	}

	public void connect(String pseudo) {
		this.clientPseudos.add(pseudo);
	}

	public void disconnect(String pseudo) {
		this.clientPseudos.remove(pseudo);
	}

	public boolean isPseudoAvailable(String pseudo) throws RemoteException {
				
		boolean isAvailable = true;
		
		Iterator<String> it = this.clientPseudos.iterator();
		while(it.hasNext() && isAvailable) {
			String currentPseudo = (String) it.next();
			if(currentPseudo.equals(pseudo)) {
				isAvailable = false;
			}
		}
		
		return isAvailable;
	}
    
    
}
