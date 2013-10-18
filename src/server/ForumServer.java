package server;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import topic.TopicInterface;

public class ForumServer extends UnicastRemoteObject implements ForumServerInterface, Serializable {

    private HashMap<String, TopicInterface> topics;

    public ForumServer() throws RemoteException {
        super();
        this.topics = new HashMap<String, TopicInterface>();
    }

    
    /**
     * Interface methods
     */
	public TopicInterface getTopic(String title) throws RemoteException {
		return null;
	}

	public ArrayList<TopicInterface> getAllTopics() throws RemoteException {
		return null;
	}
    
    
}
