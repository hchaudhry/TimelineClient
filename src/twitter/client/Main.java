package twitter.client;

import twitter4j.Twitter;

public class Main {

	public static void main(String[] args) {
		
		TwitterW tw = new TwitterW();
		
		tw.init();
		
		TimelineClient client = new TimelineClient();
		Twitter twitter = client.init();
		
		client.getUserTimeline(twitter);
	}
}
