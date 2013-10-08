import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

public class View extends JFrame implements ActionListener {
    public ForumClient client;

    public JPanel panel;
    public JTabbedPane tabbedPane;
    public JComponent panelTabMenu;
    public DefaultListModel listChansModel;
    public JTextField newTopicText;
    public JButton newTopicButton;
    public JButton delTopicButton;
    public LinkedList<ChatTab> chatTabs;

    public View() {
        try {
            this.chatTabs = new LinkedList<ChatTab>();

            // GUI build
            this.setPreferredSize(new Dimension(500, 400));
            this.panel = new JPanel();
            this.getContentPane().add(panel);

            // Tab pane
            this.tabbedPane = new JTabbedPane();

            // Menu Tab
            this.panelTabMenu = createPanel("menu");
            GridBagLayout gridbag = new GridBagLayout();
            this.panelTabMenu.setLayout(gridbag);
            
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridheight = 3;
            constraints.gridx = 0;
            constraints.gridy = 0;
            this.listChansModel = new DefaultListModel();
            JList listChans = new JList(listChansModel);
    		JScrollPane scrollableList = new JScrollPane(listChans); // Définition de la barre de scroll
    		scrollableList.setPreferredSize(new Dimension(150,200));
            this.panelTabMenu.add(listChans, constraints);

            constraints.gridheight = 1;
            constraints.gridx = 0;
            constraints.gridy = 1;
            this.newTopicText = new JTextField("");
            this.newTopicText.setPreferredSize(new Dimension(150,10));
            this.panelTabMenu.add(newTopicText, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            this.newTopicButton = new JButton("Create");
            this.newTopicButton.addActionListener(this);
            this.panelTabMenu.add(newTopicButton, constraints);

            constraints.gridx = 2;
            constraints.gridy = 1;
            this.delTopicButton = new JButton("Delete");
            this.delTopicButton.addActionListener(this);
            this.panelTabMenu.add(delTopicButton, constraints);

            this.tabbedPane.addTab("Menu", panelTabMenu);
            this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            this.add(tabbedPane);

            this.client = new ForumClient(this);
            
            this.pack();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }


    public JComponent createPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setName(text);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
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
    	this.listChansModel.addElement(title);
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
        this.chatTabs.
        	add(chatTab);
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
            /*String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                InterfaceTopic topic = this.client.topics.get(title);
                if (null == topic) {
                    this.client.addTopic(title);
                }
            }*/
        	JOptionPane.showMessageDialog(this,
        		    "Not implemented yet",
        		    "Error",
        		    JOptionPane.ERROR_MESSAGE);
            
        } else if (e.getSource() == delTopicButton) {
            String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                this.client.removeTopic(title);                
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
        		}
        	}
        }
    }
}
