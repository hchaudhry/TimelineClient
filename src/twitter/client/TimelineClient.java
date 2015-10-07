package twitter.client;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.api.UsersResources;
import twitter4j.conf.ConfigurationBuilder;

public class TimelineClient {

	private final static String CONSUMER_KEY = "IMzG6rrtvtsootv3ByTvnZY0H";
	private final static String CONSUMER_KEY_SECRET = "hnpedcqpi2auYh9eJaoCNW4s4RfmIu0MGmPo3P9ubpFGSYiuZq";

	private final static String ACCESS_TOKEN = "3862657161-RuPQkMPDDgdlWcAqJPibgZ62Jbb2V8IJgJYvyvJ";
	private final static String ACCESS_TOKEN_SECRET = "yoGMKOlmZXDTyFelbRxBNpAyyLf2psxeVnADbN6pRNSDQ";

	public TimelineClient() {

	}

	/**
	 * Initialize a session
	 * @return Twitter twitter
	 */
	public Twitter init() {

		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_KEY_SECRET)
				.setOAuthAccessToken(ACCESS_TOKEN).setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

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
