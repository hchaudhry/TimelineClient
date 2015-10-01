package twitter.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JList list;
	
	private String pictureUrl;
	
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
		
		list = new JList<>();
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(700, 410));
		scrollPane.add(list);
		
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

	public void refresh() {
		client.getHomeTimeline(twitter);
	}
}
