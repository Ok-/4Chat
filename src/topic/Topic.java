package topic;

import java.io.Serializable;
import java.rmi.RemoteException;

import client.ChatTabInterface;

public class Topic implements TopicInterface, Serializable {

	private static final long serialVersionUID = 3106817334410077716L;
	private String title;
	
	public Topic(String title) {
		this.title = title;
	}

	@Override
	public String getTopicTitle() throws RemoteException {
		return this.title;
	}

	@Override
	public void subscribe(ChatTabInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(ChatTabInterface client) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcast(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
