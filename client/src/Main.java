import java.rmi.RemoteException;

public class Main {

    public static void main(final String[] args) {
        View v = new View();
        v.setVisible(true);
        /*
        ForumClient client = null;
        try {



            client = new ForumClient();
            String title1 = "/b/";
            String title2 ="Cats";
            client.addTopic(title1);
            client.addTopic(title2);
            client.broadcast(title2, "Hello cats lovers!");
            client.removeTopic(title1);
            client.removeTopic(title2);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        */

    }
}
