package client;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatTabInterface extends Remote{

    /**
     * Display message on the client view
     * @param message A string which contains the message to display
     * @throws RemoteException
     */
    public void display(String message) throws RemoteException;
    
    /**
     * Get the pseudo of the client
     * @return this.pseudo
     * @throws RemoteException
     */
    public String getPseudo() throws RemoteException;
}
