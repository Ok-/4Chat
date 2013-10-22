package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ProviderHandler extends UnicastRemoteObject implements ProviderHandlerInterface, Serializable {

	private static final long serialVersionUID = 4351527785864353926L;
	
	
	protected ProviderHandler() throws RemoteException {
		super();
	}

}
