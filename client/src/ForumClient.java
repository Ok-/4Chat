import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class ForumClient extends UnicastRemoteObject implements InterfaceForumClient, Serializable {

    public InterfaceForumServer server;
    public LinkedHashMap<String, InterfaceTopic> topics;
    public View view;

    public ForumClient(View v) throws RemoteException {
        super();
        this.topics = new LinkedHashMap<String, InterfaceTopic>();
        this.view = v;
        try {
            server = (InterfaceForumServer) Naming.lookup("//127.0.0.1:24577/server");
            this.initialize();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get topics available on the server
     * @throws RemoteException
     */
    public void initialize() throws RemoteException {
    	ArrayList<InterfaceTopic> listOfTopics = server.getAllTopics();
    	System.out.println("Initializing " + listOfTopics.size() + " topics");
    	Iterator it = listOfTopics.iterator();
    	while(it.hasNext()) {
            InterfaceTopic topic = (InterfaceTopic) it.next();
            String title = topic.getTopic();
            this.view.addTopic(title);
    	}
    }

    /**
     * Display message on the client view
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    @Override
    public void display(String title, String message) throws RemoteException {
    	this.view.addMessage(title, message);
    }

    /**
     * Add a topic to the client list of discussions.
     * @param title The title of the topic
     */
    public void addTopic(String title) {
        try {
            InterfaceTopic topic = server.getTopic(title);
            topic.subscribe(this);
            if (!topics.containsValue(topic)) {
            	System.out.println("New topic tab : " + title);
                topics.put(title, topic);
                this.view.addTopic(title);
            }
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    /**
     * Remove a topic from the client list of discussions.
     * @param title The title of the topic
     */
    public void removeTopic(String title) {
        try {
            InterfaceTopic topic = server.getTopic(title);
            topic.unsubscribe(this);
            topics.remove(title);
            this.view.removeTopic(title);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    /**
     * Broadcast a message on a discussion thread identified by the title.
     * @param title The title of the topic
     * @param message The message to send
     */
    public void broadcast(String title, String message) {
        try {
            InterfaceTopic topic = topics.get(title);
            if (null != topic) {
                topic.broadcast(message);
            }
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
