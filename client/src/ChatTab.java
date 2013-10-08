import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatTab extends JPanel {
	public View view;
	public JTextArea textArea;
	public JTextField textField;
	public JButton sendButton;
	
	
	public ChatTab(View view, String title) {
		this.view = view;
        this.setName(title);
        this.setLayout(new GridLayout(3, 1));
        this.textArea = new JTextArea("Welcome to the #" + title + " channel!\n");
        this.textArea.setEditable(false);
        this.add(textArea);
        this.textField = new JTextField("");
        this.add(textField);
        this.sendButton = new JButton("Send");
        this.add(sendButton);
        this.sendButton.addActionListener(view);
	}
}
