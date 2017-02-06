package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import parsers.TweetPostParser;
import parsers.TweetPreParser;
import util.StringUtil;
import util.TwitterUserUtilities;

import rita.RiMarkov;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterMarkovChain {

	private static int numSentencesToGenerate;
	private static final int DEFAULT_N_FACTOR = 3;

	private class Authenticater {
		
		/* @daniel_kesner */
		//		private static final String OAuthConsumerKey = "KILn6t2caWaZZnmEnRuKunz5K";
		//		private static final String OAuthConsumerSecret = "1nGa0FBJyskHq7blwP0yfviHGdBZwJQpC2qLdVL8c0GKRrhkCk";
		//		private static final String OAuthAccessToken = "3262925426-97e7cEKFKGIac7dR9Db4yqM3HXY3xQCSgfPnHNW";
		//		private static final String OAuthAccessTokenSecret = "2CqTusgJB97YkSIWARRz5hsIgTvL7FHMQom54Lji2RXQ1";	

		/* Random_Trump */
		private static final String OAuthConsumerKey = "XdqS5t4hLbVD8iiwqzUhpsAqK";
		private static final String OAuthConsumerSecret = "2YgnMixDrCNl2IbNZP4f0ZovNKUOUOBYlzirPnhkl4J4i29daz";
		private static final String OAuthAccessToken = "826949020073406466-vLiKUbbl7Qlvjt0GwB5IwvDfzGVKoj3";
		private static final String OAuthAccessTokenSecret = "6pZeQulzclYTFN8ItQKEcNBkFXZCrjATwkxo6jFsme37V";
		
		/* Put your keys here */
		//private static final String OAuthConsumerKey = "xxxxxxxxxxx";
		//private static final String OAuthConsumerSecret = "xxxxxxxxxxxx";
		//private static final String OAuthAccessToken = "xxxxxxx";
		//private static final String OAuthAccessTokenSecret = "xxxxxxxxxx";
	}

	public static void main(String[] args) throws TwitterException, IOException {

		/* Syntax:
		 * 
		 * java TrumpMarkovChain --generateFromAllTweets username numSentences <n-factor> <--prompt> 
		 * 
		 * Generate numSentences sentences using a model trained from 
		 * all tweets on username's timeline (with an optional n-factor
		 * specified by <n-factor>). Add the --prompt flag to have the program
		 * list out all the sentences it generated and choose which ones you
		 * wish to publish to Twitter via standard input.
		 * 
		 * Ex: java TrumpMarkovChain --generateFromAllTweets realDonaldTrump 5 3 --prompt
		 * 
		 * Train a model with an n-factor of 3 using all tweets from @realDonaldTrump's timeline,
		 * output 5 randomly generated sentences, allow user to choose which of the 5 to post to Twitter.
		 * 
		 * Ex: java TrumpMarkovChain --generateFromAllTweets realDonaldTrump 5
		 * 
		 * Train a model with the default n-factor using all tweets from @realDonaldTrump's timeline,
		 * output 5 random sentences, post all 5 automatically to Twitter without prompting user.
		 * 
		 *  */

		if (args.length == 0) {
			System.out.println("Usage: java TrumpMarkovChain --generateFromAllTweets username numSentences <n-factor> <--prompt>");
			System.out.println("All arguments in angle brackets (<>) are optional");
		}

		/* Let's take a trip to the factory */

		// Twitter4j objects
		ConfigurationBuilder cb = new ConfigurationBuilder();

		// Connect to Twitter API and authenticate
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(Authenticater.OAuthConsumerKey)
		.setOAuthConsumerSecret(Authenticater.OAuthConsumerSecret)
		.setOAuthAccessToken(Authenticater.OAuthAccessToken)
		.setOAuthAccessTokenSecret(Authenticater.OAuthAccessTokenSecret);

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		// TweetParser objects 
		TweetPreParser preParser = new TweetPreParser();
		TweetPostParser postParser = new TweetPostParser();

		// Util objects
		TwitterUserUtilities userUtil = new TwitterUserUtilities();
		StringUtil stringUtil = new StringUtil();

		List<Status> tweets;
		BufferedReader readFromKeyboard;
		String twitterUsername = "";
		String allTweets = "";
		boolean promptBeforePosting = false;
		int nfactor = 0;	// initialize to 0, exception will be thrown if this is not set before call to generateSentences()

		/********* --generateFromAllTweets **********/
		if (args[0].equals("--generateFromAllTweets")) {

			// Must specify correct number of arguments (n-factor is optional)
			if (! (args.length == 5 || args.length == 4)) {
				System.out.println("ERROR: Invalid number of arguments. Please check syntax in README and try again.");
				System.exit(-1);
			}

			// Populate variables from command line args
			try {

				// If user enters @user instead of user, remove the @
				if (args[1].substring(0,1).equals("@")) {
					twitterUsername = args[1].substring(1, args[1].length());
				}

				else {
					twitterUsername = args[1];
				}

				numSentencesToGenerate = Integer.parseInt(args[2]);

				switch (args.length) {
				// Default n-factor, no prompt (flag is already set to false)
				case 3:
					nfactor = DEFAULT_N_FACTOR;

					// User specified with n-factor or prompt flag in args[3]
				case 4:
					// If final argument is --prompt, update variable
					if (args[3].equals("--prompt"))
						promptBeforePosting = true;

					// Else if final argument is a number, interpret it as n-factor
					else if (stringUtil.isNumber(args[3])) {
						nfactor = Integer.parseInt(args[3]);
					}

					else
						throw new RuntimeException("ERROR: Unable to parse final argument args[3]: " + args[3]);

					// User-defined n-factor, prompt is true
				case 5:
					nfactor = Integer.parseInt(args[3]);
					promptBeforePosting = true;
				}


			} catch (Exception ex) {
				System.out.println("ERROR: Unable to parse command line arguments.");
				System.out.println("Please ensure you follow the syntax specified in README. Arguments are case-sensitive.");
				System.out.println("Printing args[] array: ");
				for (int i = 0; i < args.length; i++) {
					System.out.println("args[" + i + "]: " + args[i]);
				}
				ex.printStackTrace();
				System.exit(-1);
			}

			/* Get all tweets from user */
			tweets = userUtil.getAllTweetsFromTimeline(twitter, twitterUsername);	

			// Concatenate all tweets into a single String to be fed to RiTa
			for (Status status : tweets) {

				String tweet = status.getText();

				try {
					// Remove newlines, "RTs", and URLs (i.e. strings including "http://")
					if (preParser.needsToBeTrimmed(tweet)){
						tweet = preParser.trimNonWords(tweet);
					}

					// Confirm that the tweet we're adding isn't just whitespace
					if (! (tweet.equals("") || tweet.equals(" "))){
						allTweets += tweet + " ";
					}

				} catch (Exception e) {
					System.out.println("Error parsing Tweet: " + tweet);
					e.printStackTrace();
				}
			}

			/* Sentence generation with RiTa */

			// RiMarkov(Object parent, int nFactor, boolean recognizeSentences, boolean allowDuplicates)
			RiMarkov rm = new RiMarkov(null, nfactor, true, true);
			rm.loadText(allTweets);
			String[] sentences = rm.generateSentences(numSentencesToGenerate);
			ArrayList<String> finalSentencesToPost = new ArrayList<String>(sentences.length);

			for (String sentence : sentences) {
				// Clean up the RiTa-generated sentences
				sentence = postParser.removeNonAlphaTokens(sentence);
				sentence = postParser.attachHashtagsAndMentions(sentence);

				// Only add final output sentence to sentenceList if (more constraints can be added later):
				// - length of total tweet is <=140 characters (tweet max length)
				if (sentence.length() < 141) 
					finalSentencesToPost.add(sentence);
			}

			// Either post directly to Twitter or ask user which sentences they wish to post 
			if (! promptBeforePosting) {
				// Post all sentences directly to Twitter
				for (String tweetToPost : finalSentencesToPost) {
					twitter.updateStatus(tweetToPost);
				}
			}

			// Else, print all generated sentences to the screen, let user decide which to publish
			else {

				System.out.println("Printing all generated sentences. At the prompt, enter either a single value OR");
				System.out.println("a comma-separated list of values of sentences to post to Twitter (i.e. '1' or '2,3,6'), or '0' to quit.");

				for (int i = 0; i < finalSentencesToPost.size(); i++) {
					System.out.println(i+1 + ": " + finalSentencesToPost.get(i));
				}

				readFromKeyboard = new BufferedReader(new InputStreamReader(System.in));
				String input = readFromKeyboard.readLine();
				
				if (input.equals("0")) 
					System.exit(0);

				// If publishing multiple sentences (given a list from user with multiple values)
				if (input.contains(",")) {

					String[] sentencesToPublish = input.split(",");

					// Publish only those sentences chosen by user
					for (int i = 0; i < sentencesToPublish.length; i++) {
						int idx = Integer.parseInt(sentencesToPublish[i]) - 1;
						twitter.updateStatus(finalSentencesToPost.get(idx));
					}
				}

				// Publish the single sentence specified by user
				else 
					twitter.updateStatus(finalSentencesToPost.get(Integer.parseInt(input)));

				System.out.println("Tweets have been posted.");
			}
		}
	}
}
