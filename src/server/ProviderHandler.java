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
			HosterInterface topicHoster = this.topics.get(name);
			if(topicHoster != null) {
				topic = topicHoster.getTopic(name);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return topic;
	}
	
	public boolean createHostedTopic(String topicTitle) {
		
		
		// First : choose one hoster
		HosterInterface lessOverloadedHoster = null;
		int lessOverloadedHosterCapacity = -1;
		
		Iterator<HosterInterface> it = hosters.iterator();
		while(it.hasNext()) {
			HosterInterface currentHoster = it.next();
			try {
				int currentHosterCapacity = currentHoster.getServerLoad();
				
				// If we didn't found an hoster yet and if currentHoster has capacity,
				// we choose it
				if(lessOverloadedHoster == null) {
					if(currentHosterCapacity < 100) {
						lessOverloadedHoster = currentHoster;
						lessOverloadedHosterCapacity = currentHosterCapacity;
					}
				}
				// If we have already find an hoster, check if currentHoster
				// has more capacity or not, so we can choose it or not
				else if(currentHosterCapacity < lessOverloadedHosterCapacity) {
					lessOverloadedHoster = currentHoster;
					lessOverloadedHosterCapacity = currentHosterCapacity;
				}
			}
			catch(RemoteException re) {
				re.printStackTrace();
			}
		}
					
			
		// Second : try to create a topic on the hoster choosen before
		boolean topicCreated = false;
		if(lessOverloadedHoster != null) {
			try {
				lessOverloadedHoster.createHostedTopic(topicTitle);
				topicCreated = true;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		return topicCreated;
	}

	
	/*
	 * Remote methods
	 */
	
	public void register(HosterInterface hoster) throws RemoteException {
		System.out.println("Registering provider");
		this.hosters.add(hoster);
	}

	public void registerTopic(String topicTitle, HosterInterface hoster) throws RemoteException {
        System.out.println("Registering topic on hoster");
		topics.put(topicTitle, hoster);
	}



}
