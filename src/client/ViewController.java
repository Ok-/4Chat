package client;

import server.ForumServerInterface;

public class ViewController {
	
	private ForumServerInterface server;
	private View view;

	public ViewController(ForumServerInterface forumServer, View v) {
		this.server = forumServer;
		this.view = v;
	}

}
