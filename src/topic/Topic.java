package topic;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.LinkedHashSet;

import client.ChatTabInterface;

public class Topic extends UnicastRemoteObject implements TopicInterface, Serializable {

	private static final long serialVersionUID = 3106817334410077716L;
	private String title;
    private LinkedHashSet<ChatTabInterface> subscribers;
	
	public Topic(String title) throws RemoteException {
		this.title = title;
		this.subscribers = new LinkedHashSet<ChatTabInterface>();
	}

	
	
	/*
	 * Remote Methods
	 */
	public String getTopicTitle() throws RemoteException {
		return this.title;
	}

	public void subscribe(ChatTabInterface client) throws RemoteException {
        this.broadcast(client.getPseudo() + " has joined");
        this.subscribers.add(client);
    }

    public void unsubscribe(ChatTabInterface client) throws RemoteException {
        this.subscribers.remove(client);
        this.broadcast(client.getPseudo() + " has left");
    }
    
	public void forceUnsubscribe(String pseudo) throws RemoteException {

        Iterator<ChatTabInterface> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
        	ChatTabInterface client = iterator.next();
        	if(client.getPseudo().equals(pseudo)) {
        		this.unsubscribe(client);
        	}
        }
	}

	public void broadcast(String message) throws RemoteException {

        Iterator<ChatTabInterface> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
        	ChatTabInterface client = iterator.next();
        	try {
        		client.display(message);
        	}
        	catch(RemoteException re) {
        		// Si on n'arrive pas à joindre le client, on le jarte
        		this.subscribers.remove(client);
        		throw new RemoteException();
        	}
        }
		
	}

}
