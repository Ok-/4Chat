package server;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainServer {

    public static void main(final String[] args) {
        
    	try {
            ForumServerInterface server = new ForumServer();
            LocateRegistry.createRegistry(24577);
            Naming.bind("//127.0.0.1:24577/server", server);
            
            /*server.addTopic("Cats");
            server.addTopic("boobizzz");
            server.addTopic("Girls");*/
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}