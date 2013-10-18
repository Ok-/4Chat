package client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InitDialogs extends JFrame {
	
	private static final long serialVersionUID = -5969015269734138105L;

	public InitDialogs() {
		super();
	}

    public String askString(String title, String message, String defaultAnswer) { 
    	return (String)JOptionPane.showInputDialog(
                this,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                defaultAnswer);
    }
}
