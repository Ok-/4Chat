package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import server.*;

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
			System.out.println("Style not found");
		}
		
		
    	 /*****************************
    	  * 
         * Connecting to the server
         * 
         *****************************/
        boolean serverReachable = false;
        String defaultServerLocation = "//127.0.0.1:24577/server";
        String message = "Server Location";
        
        while(!serverReachable) {
        	// New dialog allowing client server location
        	InitDialogs d = new InitDialogs();
        	String serverLocation = d.askString("Connecting", message, defaultServerLocation);
	        
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
        
        while(!pseudoOK) {
        	
        	// New dialog allowing client to type his pseudo
        	InitDialogs d = new InitDialogs();
        	pseudo = d.askString("Who are you ?", message, pseudo);
        	
        	if(pseudo == null) {
        		System.exit(0);
        	}
	
	        // is pseudo already picked ?
        	try {
				pseudoOK = forumServer.isPseudoAvailable(pseudo);
			} catch (RemoteException e) {
				e.printStackTrace();
				System.exit(0);
			}
	        
	        if(pseudoOK) {
	        	try {
	    			forumServer.connect(pseudo);
		        	System.out.println("Pseudo picked : " + pseudo);
	    		} catch (RemoteException e) {
	    			pseudoOK = false;
	    		}
	        }
	        else {
	        	System.out.println("Pseudo anavailable");
	        	message = "Pseudo already used, please choose another one";
	        }
        }
        

        
   	 	/*****************************
   	 	 * 
        * All required steps are done, creating window
        * 
        *****************************/
    	
        View v = new View();
        ViewController c = new ViewController(forumServer, v);
    }
    
}
