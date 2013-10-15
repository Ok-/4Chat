import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatTab extends JPanel implements KeyListener {
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
        GridBagConstraints gbc;
        panel1.setLayout(layout);

        final JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setPreferredSize(new Dimension(470, 290));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        /*gbc.weightx = 1.0;
        gbc.weighty = 1.0;*/
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(scrollPane1, gbc);
        
        textArea = new JTextArea("Welcome to the #" + title + " channel!\n");
        textArea.setEditable(false);
        scrollPane1.setViewportView(textArea);
        
        textField = new JTextField();
        textField.addKeyListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 2.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(textField, gbc);
        
        sendButton = new JButton();
        sendButton.setText("Send");
        this.sendButton.addActionListener(view);
        this.sendButton.setMnemonic(KeyEvent.VK_ENTER);
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(sendButton, gbc);
        
        final JSeparator separator1 = new JSeparator();
        separator1.setPreferredSize(new Dimension(scrollPane1.getWidth(), 10));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(separator1, gbc);
        
        unsubscribeButton = new JButton();
        unsubscribeButton.setText("Unsubscribe");
        this.unsubscribeButton.addActionListener(view);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0.1;
        //gbc.fill = GridBagConstraints.BOTH;
        panel1.add(unsubscribeButton, gbc);

        this.add(panel1);
        
        this.repaint();
	}


	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("Enter pressed");
			this.sendButton.doClick();
			this.textField.setText("");
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
}
