import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class View extends JFrame implements ActionListener, KeyListener, WindowListener {
    public ForumClient client;

    public JPanel panel;
    public JTabbedPane tabbedPane;
    public DefaultListModel listChansModel;
    public JTextField newTopicText;
    public JButton newTopicButton;
    public JButton delTopicButton;
    public JButton subscribeButton;
    public JButton updateButton;
    public JList list;
    public LinkedList<ChatTab> chatTabs;

    public View() {

        
        /*************************
         * Constructing window
         *************************/
    	
    	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	this.addWindowListener(this);
    	
        this.chatTabs = new LinkedList<ChatTab>();

        this.setPreferredSize(new Dimension(500, 400));
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        tabbedPane = new JTabbedPane();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(tabbedPane, gbc);

        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        tabbedPane.addTab("Menu", panel2);
        subscribeButton = new JButton();
        subscribeButton.setText("Subscribe");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(subscribeButton, gbc);
        this.subscribeButton.addActionListener(this);

        updateButton = new JButton();
        updateButton.setText("Update list");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(updateButton, gbc);
        this.updateButton.addActionListener(this);

        final JScrollPane scrollPane1 = new JScrollPane();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(scrollPane1, gbc);

        this.listChansModel = new DefaultListModel();
        list = new JList(listChansModel);
        scrollPane1.setViewportView(list);
        final JLabel label1 = new JLabel();
        label1.setText("Channels");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label1, gbc);

        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(separator1, gbc);

        newTopicText = new JTextField();
        this.newTopicText.addKeyListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(newTopicText, gbc);

        newTopicButton = new JButton();
        newTopicButton.setText("Create");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.newTopicButton.addActionListener(this);
        panel2.add(newTopicButton, gbc);

        delTopicButton = new JButton();
        delTopicButton.setText("Delete");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.delTopicButton.addActionListener(this);
        panel2.add(delTopicButton, gbc);

        final JLabel label2 = new JLabel();
        label2.setText("Create a new Channel");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label2, gbc);


        this.add(panel);
        this.pack();
        

        /*****************************
         * 
         * Connecting to the server
         * 
         *****************************/
        boolean serverReachable = false;
        String serverLocation = "//127.0.0.1:24577/server";
        String message = "Server Location";
        
        while(!serverReachable) {
	        String s = (String)JOptionPane.showInputDialog(
	                            this,
	                            message,
	                            "Connecting",
	                            JOptionPane.PLAIN_MESSAGE,
	                            null,
	                            null,
	                            serverLocation);
	        
	        if(s == null) {
        		this.exit();
        	}
	        
	        try {
	            this.client = new ForumClient(this, serverLocation);
	            serverReachable = true;
	        } catch (RemoteException re) {
	            re.printStackTrace();
	            message = "Server unreachable, please verify location";
	        }
        }
        
        
        /************************
         * 
         * Choosing a pseudo
         * 
         ************************/
        boolean pseudoOK = false;
        String pseudo = "Guest" + ((int)(Math.random() * (9999-1111)) + 1111);
        message = "Pick up your pseudo";
        
        while(!pseudoOK) {
	        String s = (String)JOptionPane.showInputDialog(
	                            this,
	                            message,
	                            "Who are you ?",
	                            JOptionPane.PLAIN_MESSAGE,
	                            null,
	                            null,
	                            pseudo);
	
	
	        try {
	        	if(s == null) {
	        		this.exit();
	        	}
		        if ((s != null) && (s.length() > 0)) {
		        	pseudo = s;
		        }
        		pseudoOK = this.client.setPseudo(pseudo);
		        
	        }
	        catch (RemoteException re) {
	            re.printStackTrace();
	        }
	        
	        if(pseudoOK) {
	        	System.out.println("Pseudo picked : " + pseudo);
	        }
	        else {
	        	System.out.println("Pseudo anavailable");
	        	message = "Pseudo already used, please choose another one";
	        }
        }
        
        this.setTitle(pseudo);

    }
    
    public void addMessage(String title, String message) {
    	Iterator<ChatTab> iterator = chatTabs.iterator();
    	while(iterator.hasNext()) {
    		ChatTab tab = iterator.next();
    		if (tab.getName().compareTo(title) == 0) {
    			tab.textArea.append(message + "\n");
    		}
    	}
    }

    /*
     * Server has add a topic, we must add it to the local list
     */
    public void addTopic(String title) {
        if (!this.listChansModel.contains(title)) {
            this.listChansModel.addElement(title);
        }
    }

    /*
     * Server has remove one topic, we must remove it from the local list
     */
    public void removeTopic(String title) {
    	this.listChansModel.removeElement(title);
    }
    
    public void addTab(String title) {
        ChatTab chatTab = new ChatTab(this, title);
        if(this.chatTabs == null) {
        	this.chatTabs = new LinkedList<ChatTab>();
        }
        this.chatTabs.add(chatTab);
        this.tabbedPane.addTab(title, chatTab);
    }    
    
    
    public void removeTab(String title) {
    	int tabNumber = this.tabbedPane.getTabCount();
        for(int i = 0; i < tabNumber; i++) {
            String name = this.tabbedPane.getTitleAt(i);
            if (0 == name.compareTo(title)) {
                this.tabbedPane.remove(i);
            }
        }
        Iterator<ChatTab> iterator = chatTabs.iterator();
        while (iterator.hasNext()) {
        	ChatTab tab = iterator.next();
        	if (tab.getName().compareTo(title) == 0) {
        		chatTabs.remove(tab);
        	}
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Check Menu buttons
        if (e.getSource() == newTopicButton) {
            // Add a new topic in the view list
            String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                InterfaceTopic topic = this.client.topics.get(title);
                if (null == topic) {
                    this.client.addTopic(title);
                    this.addTab(title);
                }
            }
        } else if (e.getSource() == subscribeButton) {
            String title = (String)list.getSelectedValue();
            if (null != title) {
                this.client.addTopic(title);
                this.addTab(title);
            }
        } else if (e.getSource() == delTopicButton) {
            String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                try {
                    InterfaceTopic topic = this.client.server.getTopic(title);
                    if (null != topic) {
                        if (topic.getNumberOfSubscribers() == 0) {
                            this.client.removeTopic(title);
                        }
                    }
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
            
        } else if (e.getSource() == updateButton) {
            try {
                ArrayList<InterfaceTopic> allTopics = this.client.server.getAllTopics();
                this.client.initialize();
                this.repaint();
            } catch (RemoteException re) {
                re.printStackTrace();
            }
        } else {
        	Iterator<ChatTab> iterator = chatTabs.iterator();
        	while(iterator.hasNext()) {
        		ChatTab tab = iterator.next();
        		if (e.getSource() == tab.sendButton) {
            		String text = tab.textField.getText();
            		if (text.compareTo("") != 0) {
            			String title = tab.getName();
                		this.client.broadcast(title, text);
                		tab.textField.setText("");
            		}
        		} else if (e.getSource() == tab.unsubscribeButton) {
                    try {
                    String title = tab.getName();
                        InterfaceTopic topic = this.client.server.getTopic(title);
                        topic.unsubscribe(this.client);
                        this.removeTab(title);
                    } catch (RemoteException re) {
                        re.printStackTrace();
                    }
                }
        	}
        }
    }

	public void error(boolean writeMessage, String message) {
		
		if(writeMessage) {
			JOptionPane.showMessageDialog(this,
					message,
			    "OOps",
			    JOptionPane.ERROR_MESSAGE);
		}
		System.exit(0);
	}

	public void exit() {
		
		System.exit(0);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keypressed");
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Enter pressed");
			this.newTopicButton.doClick();
			this.newTopicText.setText("");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		this.exit();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			this.client.server.disconnect(this.client);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.exit();
		
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
}
