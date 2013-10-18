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
	
	private TopicInterface topic;
	private ChatTab tab;

	protected ChatTabController(ChatTab tab, TopicInterface topic) throws RemoteException {
		super();
		
		this.tab = tab;
		this.topic = topic;
		
		// Activating listeners
        this.tab.textField.addKeyListener(this);
        this.tab.sendButton.addActionListener(this);
        this.tab.unsubscribeButton.addActionListener(this);
	}

	
	/*
	 * Remote methods
	 */
	public void display(String title, String message) throws RemoteException {
		
	}

	public String getPseudo() throws RemoteException {
		return null;
	}

	public void topicClosing(String topic) throws RemoteException {
		
	}

	
	/*
	 * Listeners methods
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
