package provider;

import java.rmi.Naming;

import server.ProviderHandlerInterface;

public class MainProvider {

	
	public static void main(String[] args) {

		ProviderHandlerInterface server = null;
    	
		try {
        	server = (ProviderHandlerInterface) Naming.lookup("//127.0.0.1:24666/server");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
	}

}
