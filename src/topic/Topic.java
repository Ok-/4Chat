package topic;

import java.io.Serializable;
import java.rmi.RemoteException;

public class Topic implements TopicInterface, Serializable {

	private static final long serialVersionUID = 3106817334410077716L;
	private String title;
	
	public Topic(String title) {
		this.title = title;
	}

	@Override
	public String getTopicTitle() throws RemoteException {
		return this.title;
	}

}
