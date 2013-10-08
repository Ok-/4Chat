import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;

public class View extends JFrame implements ActionListener {
    public ForumClient client;

    public JPanel panel;
    public JTabbedPane tabbedPane;
    public JComponent panelTabMenu;
    public JTextField newTopicText;
    public JButton newTopicButton;
    public JButton delTopicButton;
    public LinkedList<ChatTab> chatTabs;

    public View() {
        try {
            this.client = new ForumClient(this);
            this.chatTabs = new LinkedList<ChatTab>();

            // GUI build
            this.setPreferredSize(new Dimension(500, 400));
            this.panel = new JPanel();
            this.getContentPane().add(panel);

            // Tab pane
            this.tabbedPane = new JTabbedPane();

            // Menu Tab
            this.panelTabMenu = createPanel("menu");
            GridLayout layoutMenu = new GridLayout(2,2);
            this.newTopicText = new JTextField("");
            this.panelTabMenu.add(newTopicText);
            this.newTopicButton = new JButton("Subscribe");
            this.newTopicButton.addActionListener(this);
            this.panelTabMenu.add(newTopicButton);
            this.delTopicButton = new JButton("Unsubscribe");
            this.delTopicButton.addActionListener(this);
            this.panelTabMenu.add(delTopicButton);
            this.panelTabMenu.setLayout(layoutMenu);

            this.tabbedPane.addTab("Menu", panelTabMenu);
            this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            this.add(tabbedPane);
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
    
    public void addTab(String title) {
        JComponent newTab = createPanel(title);
        GridLayout layoutTab = new GridLayout();
        newTab.setLayout(layoutTab);
        ChatTab chatTab = new ChatTab(this, title);
        newTab.add(chatTab);
        this.chatTabs
        	.add(chatTab);
        this.tabbedPane.addTab(title, newTab);
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
                }
            }
            
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
