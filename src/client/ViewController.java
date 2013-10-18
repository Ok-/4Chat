package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

import server.ForumServerInterface;
import topic.TopicInterface;

public class ViewController extends UnicastRemoteObject implements ActionListener, KeyListener, WindowListener, Serializable {
	
	private static final long serialVersionUID = -8936105737211907325L;
	private ForumServerInterface server;
	private View view;
	private String pseudo;

	public ViewController(ForumServerInterface forumServer, View v, String pseudo) throws RemoteException {
		this.server = forumServer;
		this.view = v;
		this.pseudo = pseudo;
		
		// Activating listeners
    	this.view.addWindowListener(this);

        this.view.subscribeButton.addActionListener(this);
        this.view.updateButton.addActionListener(this);
        this.view.newTopicText.addKeyListener(this);
        this.view.newTopicButton.addActionListener(this);
	}
	
	
	public void updateTopicList() throws RemoteException {
		
		// Clean the topic list
		this.view.cleanTopicList();
		
    	ArrayList<TopicInterface> listOfTopics = server.getAllTopics();
    	System.out.println("Updating topic list. " + listOfTopics.size() + " found");
    	Iterator<TopicInterface> it = listOfTopics.iterator();
    	while(it.hasNext()) {
    		TopicInterface topic = (TopicInterface) it.next();
            String title = topic.getTopicTitle();
            this.view.addTopic(title);
    	}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

    	// View wants to add topic to the server
        if (e.getSource() == this.view.newTopicButton) {
        	
        	 String topicTitle = this.view.newTopicText.getText();
             if (topicTitle.compareTo("") != 0) {
            	 try {
            		 if(this.server.getTopic(topicTitle) != null) {
						 this.view.errorDialog("Topic already exists");
					 }
					 else {
						 this.server.createLocalTopic(topicTitle);
						 this.view.newTopicText.setText("");	
						 this.updateTopicList();
					 }
				} catch (RemoteException e1) {
					e1.printStackTrace();
					this.view.errorDialog("Something went wrong");
				}
             }
        	
        }
        

    	// View wants to subscribe to one topic
        else if (e.getSource() == this.view.subscribeButton) {
        	
        } 

        
    	// View wants to update chans' list
        else if (e.getSource() == this.view.updateButton) {
        	
        } 
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

		try {
			this.server.disconnect(this.pseudo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.view.newTopicButton.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
