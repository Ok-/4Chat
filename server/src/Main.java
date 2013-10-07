import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {

    public static void main(final String[] args) {
        try {
            ForumServer server = new ForumServer();
            /*
            server.addTopic("Cats");
            server.addTopic("/b/");
            */
            LocateRegistry.createRegistry(24577);
            Naming.bind("//127.0.0.1:24577/server", server);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}