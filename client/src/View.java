import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class View extends JFrame implements ActionListener {
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
        try {
            this.chatTabs = new LinkedList<ChatTab>();

            /*
            // GUI build
            //this.setPreferredSize(new Dimension(500, 400));
            this.panel = new JPanel();
            this.getContentPane().add(panel);

            // Tab pane
            this.tabbedPane = new JTabbedPane();

            // Menu Tab
            this.panelTabMenu = createPanel("menu");
            GridBagLayout gridbag = new GridBagLayout();
            this.panelTabMenu.setLayout(gridbag);
            
            
            GridBagConstraints constraints = new GridBagConstraints();
    		constraints.anchor = GridBagConstraints.NORTHWEST;
    		constraints.insets = new Insets(10, 0, 10, 0);
            constraints.fill = GridBagConstraints.BOTH;
            constraints.gridwidth = 3;
            constraints.gridx = 0;
            constraints.gridy = 0;
            this.listChansModel = new DefaultListModel();
            JList listChans = new JList(listChansModel);
    		JScrollPane scrollableList = new JScrollPane(listChans); // Définition de la barre de scroll
    		scrollableList.setPreferredSize(this.getPreferredSize());
            this.panelTabMenu.add(listChans, constraints);

            constraints.gridwidth = 1;
            constraints.gridx = 0;
            constraints.gridy = 1;
            this.newTopicText = new JTextField("");
            //this.newTopicText.setPreferredSize(new Dimension(150,10));
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
            */

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

            this.client = new ForumClient(this);

            this.pack();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
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
}
