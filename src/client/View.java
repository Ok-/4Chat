package client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class View extends JFrame {

	private static final long serialVersionUID = -4738666664476498568L;
    
    private LinkedList<ChatTab> chatTabs;
	
	public JPanel panel;
	public JTabbedPane tabbedPane;
	public DefaultListModel listChansModel;
	public JTextField newTopicText;
	public JButton newTopicButton;
	public JButton delTopicButton;
	public JButton subscribeButton;
	public JButton updateButton;
	public JList list;
	
    
    public View() {
    	
    	// Initialize objects
        this.chatTabs = new LinkedList<ChatTab>();
    	
        
        // General settings
    	this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	this.setResizable(false);

        this.setPreferredSize(new Dimension(500, 400));
        
        
        // Main panel
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

        
        // Main tab
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

        // Chans list
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

        // Update chan list button
        updateButton = new JButton();
        updateButton.setText("Update list");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(updateButton, gbc);

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

        
        // Add topic text field & button
        final JLabel label2 = new JLabel();
        label2.setText("Create a new Channel");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label2, gbc);
        
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
        panel2.add(newTopicButton, gbc);

        this.add(panel);
        this.pack();
    }
    
    public LinkedList<ChatTab> getChatTabs() {
    	return this.chatTabs;
    }
    
    public void openChatTab(ChatTab tab) {
        this.chatTabs.add(tab);
        this.tabbedPane.addTab(tab.getName(), tab);
        this.tabbedPane.setSelectedComponent(tab);
    }
    
    public void closeChatTab(ChatTab tab) {
    	
    	// First remove tab from tabbedPane
    	int tabNumber = this.tabbedPane.getTabCount();
        for(int i = 0; i < tabNumber; i++) {
            String name = this.tabbedPane.getTitleAt(i);
            if (0 == name.compareTo(tab.getName())) {
                this.tabbedPane.remove(i);
            }
        }
        
        // Then delete the chatTab
    	this.chatTabs.remove(tab);
    }
    
    public void cleanTopicList() {
    	this.listChansModel.clear();
    }
    
    public void addTopic(String title) {
    	this.listChansModel.addElement(title);
    }
    

	public void errorDialog(String message) {
		
		JOptionPane.showMessageDialog(this,
				message,
		    "OOps",
		    JOptionPane.ERROR_MESSAGE);
	}
}
