package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import provider.HosterInterface;
import topic.TopicInterface;

public interface ProviderHandlerInterface extends Remote {


    /**
     * Register the hoster on the server
     * @param hoster
     * @throws RemoteException
     */
	public void register(HosterInterface hoster) throws RemoteException;
	
	
    /**
     * Register a topic hosted by hoster
     * @param name The name of the topic
     * @param hoster References the hoster
     * @throws RemoteException
     */
	public void registerTopic(String topicTitle, HosterInterface hoster) throws RemoteException;
}
