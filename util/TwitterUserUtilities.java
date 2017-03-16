package util;

import java.util.ArrayList;

import auth.Authenticater;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

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
	
	// Returns a new Twitter object that's already been connected & authenticated
	// Pass a null String to use the default authfile path ("src/auth/keys.txt")
	public static Twitter createTwitterInstance(String pathToAuthFile) {
		
		// Instance of authentication class -- if "DEFAULT_AUTH_PATH", path is src/auth/keys.txt
		Authenticater auth_me = new Authenticater(pathToAuthFile);
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
		// Connect to Twitter API and authenticate
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(auth_me.getConsumerKey())
		.setOAuthConsumerSecret(auth_me.getConsumerSecret())
		.setOAuthAccessToken(auth_me.getAccessToken())
		.setOAuthAccessTokenSecret(auth_me.getAccessTokenSecret());
		
		return new TwitterFactory(cb.build()).getInstance();
	}

}
