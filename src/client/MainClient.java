package client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.UIManager;

import server.ForumServerInterface;

public class MainClient {

    public static void main(final String[] args) {
    	
    	ForumServerInterface forumServer = null;
    	

    	/******************
    	 * 
    	 * Setting GTK look
    	 * 
    	 ******************/
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		}
		catch(Exception e) {
		}
		
		
    	 /*****************************
    	  * 
         * Connecting to the server
         * 
         *****************************/
        boolean serverReachable = false;
        String defaultServerLocation = "//127.0.0.1:24577/server";
        String message = "Server Location";
        
        // Asking the server location until we will be able to establish connection (or until user cancel)
    	InitDialogs d = new InitDialogs();
        while(!serverReachable) {
        	String serverLocation = d.askString("Connecting", message, defaultServerLocation);
	        
        	// Cancel asked
	        if(serverLocation == null) {
        		System.exit(0);
        	}
	        
	        try {
	        	forumServer = (ForumServerInterface) Naming.lookup(serverLocation);
	            serverReachable = true;
	        } catch (Exception e) {
	            e.printStackTrace();
	            message = "Server unreachable, please verify location";
	        }
        }

        
        
   	 	/*****************************
   	 	 * 
        * Choosing pseudo
        * 
        *****************************/
        
        boolean pseudoOK = false;
        String pseudo = "Guest" + ((int)(Math.random() * (9999-1111)) + 1111);
        message = "Pick up your pseudo";
        
        // Asking a pseudo to user until he choose one available
        while(!pseudoOK) {
        	pseudo = d.askString("Who are you ?", message, pseudo);
        	
        	
        	// Cancel asked
        	if(pseudo == null) {
        		System.exit(0);
        	}
	
	        // is pseudo already picked ?
        	try {
				pseudoOK = forumServer.isPseudoAvailable(pseudo);
			} catch (RemoteException e) {
				e.printStackTrace();
	        	message = "Server is unreachable, please try later";
	        	serverReachable = false;
			}
	        
	        if(pseudoOK) {
	        	try {
	    			forumServer.connect(pseudo);
	    		} catch (RemoteException e) {
	    			pseudoOK = false;
		        	serverReachable = false;
	    		}
	        }
	        else {
	        	message = "Pseudo already used, please choose another one";
	        }
	        if(!serverReachable) {
	        	message = "Server is unreachable, please try later";
	        }
        }
        

        
   	 	/*****************************
   	 	 * 
        * All required steps are done, creating window
        * 
        *****************************/

        try {
	        View v = new View();
	        v.setTitle("4Chat - " + pseudo);
	        ViewController c = new ViewController(forumServer, v, pseudo);
	        v.setVisible(true);
			c.updateTopicList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
}
