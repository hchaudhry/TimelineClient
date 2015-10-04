package twitter.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
	private JButton post;
	private JLabel picture;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<String> list;
	private DefaultListModel<String> tweets;
	private JPanel textPanel;
	
	
	
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
		textField.setPreferredSize(new Dimension(500, 410));
		post = new JButton("Post");
		
		post.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    postStatus();
		  }
		});
		
		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textField, BorderLayout.WEST);
		textPanel.add(post, BorderLayout.EAST);
		
		tweets = new DefaultListModel<>();
		tweets = refresh();
		list = new JList(tweets);
		
		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(700, 410));
		scrollPane.setBackground(Color.BLUE);
		
		this.setLayout(new BorderLayout());
		this.getContentPane().add(textPanel, BorderLayout.CENTER);
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

	public DefaultListModel<String> refresh() {
		List<Status> statuses = null;
		statuses = client.getHomeTimeline(twitter);
		
		DefaultListModel<String> data = new DefaultListModel<String>();
		
		for (Status s : statuses) {
			data.addElement(s.getText());
		}
		
		return data;
	}
	
	public void postStatus() {
		String msg = textField.getText();
		String status = client.updateStatus(twitter, msg);
		tweets.insertElementAt(status, 0);
	}
}
