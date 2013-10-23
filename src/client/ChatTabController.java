package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import topic.TopicInterface;

public class ChatTabController extends UnicastRemoteObject implements ChatTabInterface, KeyListener, ActionListener, Serializable {

	private static final long serialVersionUID = 4228007800628222274L;
	
	private TopicInterface remoteTopic;
	private ChatTab tab;
	private String pseudo;

	protected ChatTabController(ChatTab tab, TopicInterface topic, String pseudo) throws RemoteException {
		super();
		
		this.tab = tab;
		this.remoteTopic = topic;
		this.pseudo = pseudo;
		
		// Subscribe
		this.remoteTopic.subscribe(this);
		
		// Activating listeners
        this.tab.textField.addKeyListener(this);
        this.tab.sendButton.addActionListener(this);
        this.tab.unsubscribeButton.addActionListener(this);
	}
	
	
	public void sendMessage(String text) {
		
		try {
			this.remoteTopic.broadcast("<" + this.pseudo + "> : " + text);
		} catch (RemoteException e1) {
			try {
				this.display("Your message has not been sent : " + text);
			} catch (RemoteException e2) {
				this.tab.view.errorDialog("Something went wrong");
				e2.printStackTrace();
			}
		}
	}

	
	/*
	 * Remote methods
	 */
	public void display(String message) throws RemoteException {
		this.tab.textArea.append(message + "\n");
		
	}

	public String getPseudo() throws RemoteException {
		return this.pseudo;
	}
	

	
	/*
	 * Listeners methods
	 */
	public void actionPerformed(ActionEvent e) {
		
		// User wants to send a message
		if (e.getSource() == tab.sendButton) {
    		String text = this.tab.textField.getText();
    		
    		if (text.compareTo("") != 0) {
    			this.tab.textField.setText("");
        		this.sendMessage(text);
    		}
    		
		}
		
		// User wants to close this tab
		else if (e.getSource() == tab.unsubscribeButton) {
			
			try {
				remoteTopic.unsubscribe(this);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			this.tab.view.closeChatTab(this.tab);
			
        }
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.tab.sendButton.doClick();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
