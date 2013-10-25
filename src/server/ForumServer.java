package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import exceptions.ServerOverloadedException;
import exceptions.UniqueTitleViolationException;
import topic.Topic;
import topic.TopicInterface;

public class ForumServer extends UnicastRemoteObject implements ForumServerInterface, Serializable {
	
	private static final long serialVersionUID = -7611888499866806681L;
	
	private HashMap<String, TopicInterface> topics;
	private LinkedList<String> clientPseudos; // This is for checking pseudo availability
	private ProviderHandler providers;
	private int capacity;	
	

    public ForumServer(ProviderHandler p, int capacity) throws RemoteException {
        super();
        this.topics = new HashMap<String, TopicInterface>();
        this.clientPseudos = new LinkedList<String>();
        this.providers = p;
        this.capacity = capacity;
    }

	public int getServerLoad() {
		// TODO prendre en compte le nombre de personnes
		return (topics.size() * 100) / this.capacity;
	}
	
	public boolean createLocalTopic(String topicTitle) throws UniqueTitleViolationException{
		boolean topicCreated = false;
		
		// Create topic only if there is capacity for
		// TODO : récupérer la charge serveur avec getServerLoad()
		System.out.println("capacity = " + this.topics.size() + "/" + this.capacity);
		if(this.topics.size() < this.capacity) {
			System.out.println("Capacity OK");
			TopicInterface topic;
			try {
				topic = new Topic(topicTitle);
		        topics.put(topicTitle, topic);
		        topicCreated = true;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return topicCreated;
	}

    
    /**
     * Remote methods
     */

	public void connect(String pseudo) throws RemoteException {
		this.clientPseudos.add(pseudo);
	}

	public void disconnect(String pseudo) throws RemoteException {
		// Remove client from the list of pseudos
		this.clientPseudos.remove(pseudo);
		
		// Force unsubscribe this client from all local topics
		ArrayList<TopicInterface> allTopics = this.getAllTopics();
		Iterator<TopicInterface> it = allTopics.iterator();
		while(it.hasNext()) {
			TopicInterface currentTopic = (TopicInterface) it.next();
			currentTopic.forceUnsubscribe(pseudo);
		}
		
		// Then, force unsubscribe this client from all remote topics
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
	
	public boolean isTopicTitleAvailable(String topicTitle) throws RemoteException {
		
		return true;
	}
	
	public TopicInterface getTopic(String title) throws RemoteException {
		TopicInterface topic = this.topics.get(title);
		
		// Si pas trouvé localement, on cherche chez les hosters
		if(topic == null) {
			topic = providers.getTopic(title);
		}
		
		return topic;
	}

	public ArrayList<TopicInterface> getAllTopics() throws RemoteException {
		ArrayList<TopicInterface> allTopics = new ArrayList<TopicInterface>(this.topics.values());
		allTopics.addAll(this.providers.getAllTopicsHosted());
		
		return allTopics;
	}

	public void createTopic(String topicTitle) throws RemoteException, UniqueTitleViolationException, ServerOverloadedException {
		
		// Try to create the topic on one of the providers
		boolean topicCreated = this.providers.createHostedTopic(topicTitle);
		
		// If it's not possible, create a topic locally
		if(!topicCreated) {
			topicCreated = this.createLocalTopic(topicTitle);
			if(topicCreated) {
				System.out.println("Topic " + topicTitle + " created locally (on the server)");
			}
		}
		else {
			System.out.println("Topic " + topicTitle + " created on one provider");
		}
		
		// If it's no more possible, raise an exception
		if(!topicCreated) {
			System.out.println("Topic " + topicTitle + " has not been created (server overloaded)");
			throw new ServerOverloadedException("server overloaded");
		}
	}
    
    
}
