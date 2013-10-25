package provider;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.UniqueTitleViolationException;
import server.ProviderHandlerInterface;
import topic.Topic;
import topic.TopicInterface;

public class Hoster extends UnicastRemoteObject implements HosterInterface, Serializable {

	private static final long serialVersionUID = 6889498618732612772L;
	
	private ProviderHandlerInterface server;
	private HashMap<String, TopicInterface> topics;
	private int capacity;
	
	protected Hoster(ProviderHandlerInterface server, int capacity) throws RemoteException {
		super();
		
		this.server = server;
		this.topics = new HashMap<String, TopicInterface>();
		this.capacity = capacity;
		
		try {
			this.server.register(this);
		} catch(RemoteException re) {
			re.printStackTrace();
		}
	}


	/*
	 * Remote methods
	 */

	public TopicInterface getTopic(String title) throws RemoteException {
		return this.topics.get(title);
	}

	public ArrayList<TopicInterface> getAllTopics() throws RemoteException {
		return new ArrayList<TopicInterface>(this.topics.values());
	}

	public void createHostedTopic(String topicTitle) throws RemoteException, UniqueTitleViolationException {
		
		if(this.topics.size() < this.capacity) {
			TopicInterface topic;
			topic = new Topic(topicTitle);
	        this.topics.put(topicTitle, topic);
	        this.server.registerTopic(topicTitle, this);
		}
		else {
			throw new RemoteException("hoster overloaded");
		}
	}

	public int getServerLoad() throws RemoteException {
		// TODO prendre en compte le nombre de personnes
		return (topics.size() * 100) / this.capacity;
	}


}
