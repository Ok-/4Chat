import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Display message on the client view
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    @Override
    public void display(String title, String message) throws RemoteException {
        //this.view.;
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
                topics.put(title, topic);
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
