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
		System.out.println("Subscribing");
        this.subscribers.add(client);
    }

    public void unsubscribe(ChatTabInterface client) throws RemoteException {
        this.subscribers.remove(client);
    }

	public void broadcast(String message) throws RemoteException {

        Iterator<ChatTabInterface> iterator = subscribers.iterator();
        System.out.print("Broadcasting to : ");
        while (iterator.hasNext()) {
        	ChatTabInterface client = iterator.next();
        	System.out.print(client.getPseudo() + ", ");
            client.display(message);
        }
		
	}

}
