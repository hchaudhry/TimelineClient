package twitter.client;

import javax.swing.JFrame;

public class TwitterW {

	public TwitterW() {
		
	}
	
	public void init() {
		JFrame frame = new JFrame();
		frame.setTitle("Twitter RESTFul Client");
		frame.setSize(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
}
