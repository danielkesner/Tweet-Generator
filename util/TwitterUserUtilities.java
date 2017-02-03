package util;

import java.util.ArrayList;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/***********
 * @author Daniel Kesner
 * 
 *  UserUtilities.java contains methods that involve Twitter objects
 *  interacting with Twitter users.
 *  ************/

public class TwitterUserUtilities {

	// Reads all tweets from twitterUsername's timeline, stores raw text payload into ArrayList
	public ArrayList<Status> getAllTweetsFromTimeline(Twitter twitterInstance, String twitterUsername) throws TwitterException {
		ArrayList<Status> tweets = new ArrayList<Status>();
		int pageno = 1;
		
		while (true) {

			int size = tweets.size(); 
			Paging page = new Paging(pageno++, 100);
			tweets.addAll(twitterInstance.getUserTimeline(twitterUsername, page));
			if (tweets.size() == size)
				break;
		}
		return tweets;
	}
}
