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
    public LinkedList<JPanel> chatPanels;

    public View() {
        try {
            this.client = new ForumClient();
            this.chatPanels = new LinkedList<JPanel>();

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

    public JComponent createDialogPanel(String title) {
        JPanel panel = new JPanel(false);
        panel.setName(title);
        panel.setLayout(new GridLayout(3, 1));
        JTextArea chatArea = new JTextArea("Welcome to the #" + title + " channel!\n");
        chatArea.setEditable(false);
        panel.add(chatArea);
        JTextField textField = new JTextField("");
        panel.add(textField);
        JButton sendButton = new JButton("Send");
        panel.add(sendButton);
        this.chatPanels.add(panel);

        return panel;
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
                    JComponent newTab = createPanel(title);
                    GridLayout layoutTab = new GridLayout();
                    newTab.setLayout(layoutTab);
                    newTab.add(this.createDialogPanel(title));
                    this.tabbedPane.addTab(title, newTab);
                }
            }
        } else if (e.getSource() == delTopicButton) {
            String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                this.client.removeTopic(title);
                int tabNumber = this.tabbedPane.getTabCount();
                for(int i = 0; i < tabNumber; i++) {
                    String name = this.tabbedPane.getTitleAt(i);
                    if (0 == name.compareTo(title)) {
                        this.tabbedPane.remove(i);
                    }
                }
            }
        }

        // Check Send buttons
        // TODO : for each chat tab, check if the send button was pressed
    }
}
