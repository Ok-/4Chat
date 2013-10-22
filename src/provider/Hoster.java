package provider;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Hoster extends UnicastRemoteObject implements HosterInterface, Serializable {

	protected Hoster() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 6889498618732612772L;


}
