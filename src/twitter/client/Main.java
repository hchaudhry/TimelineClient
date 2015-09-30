package twitter.client;

import twitter4j.Twitter;

public class Main {

	public static void main(String[] args) {
		
		TimelineClient client = new TimelineClient();
		Twitter twitter = client.init();
		
		TwitterW tw = new TwitterW();
		tw.init();
		
//		tw.refresh();
		
//		client.getHomeTimeline(twitter);
//		client.getUserTimeline(twitter, "imrankhanworld");
//		client.updateStatus(twitter, "Tweet with Twitter4J");
	}
}
