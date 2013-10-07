import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

public class View extends JFrame implements ActionListener {
    public ForumClient client;

    public JPanel panel;
    public JTabbedPane tabbedPane;
    public JComponent panelTabMenu;
    public JButton newTopicButton;
    public JTextField newTopicText;

    public View() {
        try {
            this.client = new ForumClient();

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
            panelTabMenu.add(newTopicText);
            this.newTopicButton = new JButton("New Topic");
            this.newTopicButton.addActionListener(this);
            panelTabMenu.add(newTopicButton);
            this.panelTabMenu.setLayout(layoutMenu);

            this.tabbedPane.addTab("Menu", panelTabMenu);
            this.tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

            this.add(tabbedPane);
            this.pack();
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }


    protected JComponent createPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newTopicButton) {
            String title = newTopicText.getText();
            if (title.compareTo("") != 0) {
                this.client.addTopic(title);
                JComponent newTab = createPanel(title);
                GridLayout layoutTab = new GridLayout();
                this.panelTabMenu.setLayout(layoutTab);
                this.tabbedPane.addTab(title, newTab);
                //this.repaint();
            }
        }
    }
}
