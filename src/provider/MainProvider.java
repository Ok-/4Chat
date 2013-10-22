package provider;

import java.rmi.Naming;

import server.ProviderHandlerInterface;

public class MainProvider {

	
	public static void main(String[] args) {

		ProviderHandlerInterface server = null;
		int topicCapacity = 100;
    	
		try {
        	server = (ProviderHandlerInterface) Naming.lookup("//127.0.0.1:24666/server");
    		HosterInterface hoster = new Hoster(server, topicCapacity);
    		if(args.length > 0) {
    			hoster.createHostedTopic(args[0]);
    			server.registerTopic(args[0], hoster);
    		}
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
		
		
		
	}

}
