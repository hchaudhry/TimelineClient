package twitter.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterW extends JFrame{

	private Twitter twitter;
	private TimelineClient client;
	
	private JButton refresh;
	private JLabel picture;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<Status> list;
	
	
	public TwitterW() {
		client = new TimelineClient();
		twitter = client.init();
	}
	
	public void init() {
		this.setTitle("Twitter RESTFul Client");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		refresh = new JButton("Update");
		
		picture = new JLabel();
		picture.setIcon(getUserPicture());
		
		textField = new JTextField();
		
//		String[] data = {"toto", "titi", "tata"};
//		list = new JList<String>(data);
		list = refresh();
		
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(700, 410));
		scrollPane.setBackground(Color.BLUE);
		
		this.setLayout(new BorderLayout());
		this.getContentPane().add(textField, BorderLayout.CENTER);
	    this.getContentPane().add(scrollPane, BorderLayout.NORTH);
	    this.getContentPane().add(picture, BorderLayout.WEST);
	    this.getContentPane().add(refresh, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	public ImageIcon getUserPicture(){
		User user = null;
		try {
			user = twitter.showUser(twitter.getId());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		String urlText = user.getProfileImageURL();
		URL url = null;
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageIcon img = new ImageIcon(url);
		return img;
	}

	public JList<Status> refresh() {
		List<Status> statuses = null;
		statuses = client.getHomeTimeline(twitter);
		
		ArrayList<String> data = new ArrayList<String>();
		
		for (Status s : statuses) {
			data.add(s.getText());
		}
		
		JList<Status> list = new JList(data.toArray());
		
		return list;
	}
}
