package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import server.ForumServerInterface;
import topic.TopicInterface;

public class ViewController implements ActionListener, KeyListener, WindowListener, MouseListener {
		
	private ForumServerInterface server;
	private View view;
	private String pseudo;

	public ViewController(ForumServerInterface forumServer, View v, String pseudo) {
		super();
		
		this.server = forumServer;
		this.view = v;
		this.pseudo = pseudo;
		
		// Activating listeners
    	this.view.addWindowListener(this);

        this.view.subscribeButton.addActionListener(this);
        this.view.updateButton.addActionListener(this);
        this.view.newTopicText.addKeyListener(this);
        this.view.newTopicButton.addActionListener(this);
        this.view.list.addMouseListener(this);
	}
	
	
	public void updateTopicList() throws RemoteException {
		
		// Clean the topic list
		this.view.cleanTopicList();
		
		// Get all remote topics
    	ArrayList<TopicInterface> listOfTopics = server.getAllTopics();
    	Iterator<TopicInterface> it = listOfTopics.iterator();
    	
    	// Add them one by one to the topic list
    	while(it.hasNext()) {
    		TopicInterface topic = (TopicInterface) it.next();
            String title = topic.getTopicTitle();
            this.view.addTopic(title);
    	}
	}
	
	public void createTopic(String topicTitle) {
		if (topicTitle.compareTo("") != 0) {
			try {
				if(this.server.getTopic(topicTitle) != null) {
					this.view.errorDialog("Topic already exists");
				}
				else {
					this.server.createTopic(topicTitle);
					this.view.newTopicText.setText("");	
					this.updateTopicList();
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
				this.view.errorDialog("Something went wrong");
			}
        }
	}
	
	public void subscribeTopic(String topicTitle) {
		
		// Check if client has already subscribe
		Iterator<ChatTab> iterator = this.view.getChatTabs().iterator();
    	boolean exists = false;
    	while (iterator.hasNext()) {
    		ChatTab tab = iterator.next();
    		exists |= (tab.getName().compareTo(topicTitle) == 0);
    	}
    	
    	// If not, subscribe !
    	if (!exists) {
			try {
	    		// Get the remote topic
	    		TopicInterface topic = this.server.getTopic(topicTitle);
	    		
	    		// Create a new tab
	            ChatTab chatTab = new ChatTab(this.view, topicTitle);
	            new ChatTabController(chatTab, topic, this.pseudo);
	            this.view.openChatTab(chatTab);
	            
			} catch (RemoteException e) {
				e.printStackTrace();
				this.view.errorDialog("Something went wrong");
			}
    	}
	}
	
	
	/*
	 * Listeners methods
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

    	// View wants to add topic to the server
        if (e.getSource() == this.view.newTopicButton) {
        	 this.createTopic(this.view.newTopicText.getText());
        }
        
    	// View wants to subscribe to one topic
        else if (e.getSource() == this.view.subscribeButton) {
        	String topicTitle = (String)this.view.list.getSelectedValue();
            if (null != topicTitle) {
            	this.subscribeTopic(topicTitle);
            }
        }
        
    	// View wants to update chans' list
        else if (e.getSource() == this.view.updateButton) {
        	try {
				this.updateTopicList();
			} catch (RemoteException e1) {
				this.view.errorDialog("Something went wrong");
				e1.printStackTrace();
			}
        } 
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {

		try {
			this.server.disconnect(this.pseudo);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(0);
		}
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.view.newTopicButton.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {

		if(e.getSource() == this.view.list) {
			if(e.getClickCount() == 2){
		    	this.subscribeTopic((String)this.view.list.getSelectedValue());
			}
		}
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

}
