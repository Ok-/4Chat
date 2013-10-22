package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import provider.HosterInterface;
import topic.TopicInterface;

public class ProviderHandler extends UnicastRemoteObject implements ProviderHandlerInterface, Serializable {

	private static final long serialVersionUID = 4351527785864353926L;
	
	private LinkedList<HosterInterface> hosters;
	private HashMap<String, HosterInterface> topics;
	
	
	protected ProviderHandler() throws RemoteException {
		super();

		this.hosters = new LinkedList<HosterInterface>();
		this.topics = new HashMap<String, HosterInterface> ();
	}


	public ArrayList<TopicInterface> getAllTopicsHosted() {

		ArrayList<TopicInterface> allTopics = new ArrayList<TopicInterface>();
		
		Iterator<HosterInterface> it = this.hosters.iterator();
		while(it.hasNext()) {
			try {
				allTopics.addAll(it.next().getAllTopics());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return allTopics;		
	}
	
	public TopicInterface getTopic(String name) {
		TopicInterface topic = null;
		try {
			topic = topics.get(name).getTopic(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return topic;
	}
	
	/*
	 * Remote methods
	 */
	
	public void register(HosterInterface hoster) throws RemoteException {
		this.hosters.add(hoster);
	}

	public void registerTopic(String topicTitle, HosterInterface hoster) throws RemoteException {
        topics.put(topicTitle, hoster);
	}

}
