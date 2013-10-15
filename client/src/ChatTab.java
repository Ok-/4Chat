import java.awt.*;

import javax.swing.*;


public class ChatTab extends JPanel {
	public View view;
	public JTextArea textArea;
	public JTextField textField;
	public JButton sendButton;
    public JButton unsubscribeButton;
	
	
	public ChatTab(View view, String title) {
        this.view = view;
        this.setName(title);

        final JPanel panel1 = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        panel1.setLayout(layout);
        textArea = new JTextArea("Welcome to the #" + title + " channel!\n");
        textArea.setEditable(false);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 10.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textArea, gbc);
        sendButton = new JButton();
        this.sendButton.addActionListener(view);
        sendButton.setText("Send");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(sendButton, gbc);
        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.CENTER;
        panel1.add(separator1, gbc);
        unsubscribeButton = new JButton();
        unsubscribeButton.setText("Unsubscribe");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(unsubscribeButton, gbc);
        textField = new JTextField();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 2.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(textField, gbc);

        this.add(panel1);
        this.repaint();
	}
}
