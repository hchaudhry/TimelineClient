package twitter.client;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TimelineClient {

	private final static String CONSUMER_KEY = "xxx";
	private final static String CONSUMER_KEY_SECRET = "xxx";

	public TimelineClient() {

	}

	/**
	 * Initialize a session
	 * @return Twitter twitter
	 */
	public Twitter init() {

		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
				.setOAuthAccessToken(null).setOAuthAccessTokenSecret(null);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		RequestToken requestToken = null;

		try {
			requestToken = twitter.getOAuthRequestToken("oob");
		} catch (TwitterException e1) {
			e1.printStackTrace();
		}

		AccessToken accessToken = null;
		while (null == accessToken) {
			String pin = TwitterW.showPinDialog(requestToken.getAuthenticationURL());
			try {
				if (pin.length() > 0) {
					accessToken = twitter.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}

		return twitter;
	}

	/**
	 * Get the current user home timeline
	 * @param twitter Twitter instance
	 * @return A list of statuses
	 */
	public List<Status> getHomeTimeline(Twitter twitter) {

		List<Status> statuses = null;
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return statuses;
	}

	/**
	 * Get statuses of a friend
	 * @param twitter Twitter instance
	 * @param friendName friend's name
	 * @return A list of statuses
	 */
	public List<Status> getUserTimeline(Twitter twitter, String friendName) {

		List<Status> statuses = null;

		try {
			statuses = twitter.getUserTimeline(friendName);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return statuses;
	}

	/**
	 * Update status
	 * @param twitter Twitter instance
	 * @param status The status to post
	 * @return 
	 */
	public Status updateStatus(Twitter twitter, String status) {
		Status st = null;

		try {
			st = twitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		return st;
	}

	/**
	 * Retweet a status
	 * @param twitter Twitter instance
	 * @param statusId status id
	 */
	public void retweetStatus(Twitter twitter, Long statusId) {

		try {
			twitter.retweetStatus(statusId);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reply to a tweet
	 * @param twitter Twitter instance
	 * @param status Status object
	 * @param response Status to post
	 */
	public void replyToTweet(Twitter twitter, Status status, String response) {
		StatusUpdate st = new StatusUpdate(response);
		st.inReplyToStatusId(status.getId());
		try {
			twitter.updateStatus(st);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get friends of user
	 * @param twitter Twitter instance
	 * @return friends List of User
	 */
	public List<User> getUserFriends(Twitter twitter) {
		User u = null;
		List<User> friends = new ArrayList<User>();
		try {
			u = twitter.showUser(twitter.getScreenName());
		} catch (IllegalStateException | TwitterException e) {
			e.printStackTrace();
		}
		
		try {
			friends = twitter.getFriendsList(u.getId(), -1);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return friends;
	}
}
