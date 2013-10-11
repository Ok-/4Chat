import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceForumClient extends Remote{

    /**
     * Display message on the client view
     * @param message A string which contains the message to send
     * @throws RemoteException
     */
    public void display(String title, String message) throws RemoteException;
    
    /**
     * Get the pseudo of the client
     * @return this.pseudo
     * @throws RemoteException
     */
    public String getPseudo() throws RemoteException;
}
