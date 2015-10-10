package twitter.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class TwitterW extends JFrame {

	private Twitter twitter;
	private TimelineClient client;

	private JButton refresh;
	private JButton post;
	private JLabel picture;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<Status> list;
	private DefaultListModel<Status> tweets;
	private JPanel textPanel;
	private JButton friends;

	public TwitterW() {
		client = new TimelineClient();
		twitter = client.init();
	}

	/**
	 * Initial the window
	 */
	public void init() {
		this.setTitle("Twitter RESTFul Client");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		refresh = new JButton("Update");

		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tweets.clear();
				tweets = refresh();
				list.setModel(tweets);
			}
		});

		picture = new JLabel();
		picture.setIcon(getUserPicture());

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(400, 410));
		post = new JButton("Post");

		post.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postStatus();
			}
		});
		
		friends = new JButton("Friends");
		
		friends.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getFriends();
			}
		});

		textPanel = new JPanel();
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textField, BorderLayout.WEST);
		textPanel.add(post, BorderLayout.CENTER);
		textPanel.add(friends, BorderLayout.EAST);

		tweets = new DefaultListModel<Status>();
		tweets = refresh();
		list = new JList(tweets);
		ListCellRenderer renderer = new CustomCellRenderer();
		list.setCellRenderer(renderer);

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

	/**
	 * Get the picture of the user
	 * To be used in window
	 * @return ImageIcon the icon of the user's picture
	 */
	public ImageIcon getUserPicture() {
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

	/**
	 * Refresh user timeline
	 * @return List of Status
	 */
	public DefaultListModel<Status> refresh() {
		List<Status> statuses = null;
		statuses = client.getHomeTimeline(twitter);

		DefaultListModel<Status> data = new DefaultListModel<Status>();

		for (Status s : statuses) {
			data.addElement(s);
		}

		return data;
	}

	/**
	 * Post a new status
	 */
	public void postStatus() {
		String msg = textField.getText();
		Status status = client.updateStatus(twitter, msg);
		tweets.insertElementAt(status, 0);
	}
	
	/**
	 * Used to show the user's friends in a combo box
	 * When a friend is selected his timeline is fetched
	 */
	public void getFriends() {
		
		List<User> users = client.getUserFriends(twitter);
		
		String[] friends = new String[users.size()];
		for (int i = 0; i < users.size(); i++) {
			friends[i] = users.get(i).getScreenName();
		}
		
		String selectedFriend = (String) JOptionPane.showInputDialog(null,
		"Please select a friend :",
		null,
		JOptionPane.QUESTION_MESSAGE,
		null,
		friends,
		friends[0]);
		
		selectedFriend = selectedFriend.replace("@", "");
		
		List<Status> friendStatus = client.getUserTimeline(twitter, selectedFriend);
		
		DefaultListModel<Status> data = new DefaultListModel<Status>();

		for (Status s : friendStatus) {
			data.addElement(s);
		}

		tweets.clear();
		tweets = data;
		list.setModel(tweets);
	}
	
	/**
	 * Used to show url to get pin
	 * @param url the url to get the pin
	 * @return the pin code
	 */
	public static String showPinDialog(String url){
	    JFrame frame = new JFrame("Twitter RESTFul Client");
	     
	    JTextPane textPane = new JTextPane();
	    textPane.setContentType("text/html");
	    textPane.setText("<html><p>Open this URL in your browser and paste the PIN code below : </p>" + url + "</html>");
	    textPane.setEditable(false);
	    textPane.setBackground(null);
	    textPane.setBorder(null);
	    
	    String pin = JOptionPane.showInputDialog(frame,
	        textPane);
	    
	    return pin;
	}
}
