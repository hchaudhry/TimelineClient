package twitter.client;

import java.util.List;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TimelineClient {

	private final static String CONSUMER_KEY = "xxx";
	private final static String CONSUMER_KEY_SECRET = "xxx";

	private final static String ACCESS_TOKEN = "xxx";
	private final static String ACCESS_TOKEN_SECRET = "xxx";

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

		 System.out.println("Showing home timeline.");
		
		 for (Status status : statuses) {
		 System.out.println(status.getUser().getName() + ":" +
		 status.getText());
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

		// System.out.println("Showing home timeline.");
		//
		// for (Status status : statuses) {
		// System.out.println(status.getUser().getName() + ":" +
		// status.getText());
		// }

		return statuses;
	}

	/**
	 * Update status
	 * @param twitter Twitter instance
	 * @param status The status to post
	 */
	public void updateStatus(Twitter twitter, String status) {
		Status st = null;

		try {
			st = twitter.updateStatus(status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}

		System.out.println("Successfully updated the status to [" + st.getText() + "].");
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
}
