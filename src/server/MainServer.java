package server;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MainServer {

    public static void main(final String[] args) {
    	 
    	
    	// Init connection with providers
    	try {
            ProviderHandlerInterface providers = new ProviderHandler();
            LocateRegistry.createRegistry(24666);
            Naming.bind("//127.0.0.1:24666/server", providers);
            
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
    	
    	// Init connection with clients
    	try {
            ForumServerInterface server = new ForumServer();
            LocateRegistry.createRegistry(24577);
            Naming.bind("//127.0.0.1:24577/server", server);
            
            server.createLocalTopic("Cats");
            server.createLocalTopic("boobizzz");
            server.createLocalTopic("Girls");
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}