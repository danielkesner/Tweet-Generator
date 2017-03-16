package timeseries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import auth.Authenticater;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import util.TwitterUserUtilities;

public class TimeSeriesUtilities {
	

	public static void writeTSToFile(String twitterUsername, String pathToFile, Twitter twitterInstance) throws TwitterException {
		ArrayList<Status> tweets = new TwitterUserUtilities().getAllTweetsFromTimeline(twitterInstance, twitterUsername);
		
		try {
		
		File ts_file = new File(pathToFile);
		if (!ts_file.exists())
			ts_file.createNewFile();
		
		FileWriter write = new FileWriter(ts_file);
		
		} catch (IOException ioe) {
			System.out.println("ERROR initializing FileWriter -- ensure that you passed a valid path");
			System.exit(-1);
		}
		
		for (Status status : tweets) {
			Date timePosted = status.getCreatedAt();
			//System.out.println(timePosted.toString());
			
			// Write to file
			/* TODO:
			 * Convert all times relative to the 24hr clock

		00:00 is t=0
		04:20 is t=4.33333 
		12:00 is t=12
		16:00 is t=16 etc
		Wed Mar 23 12:54:59 PDT 2016
		Wed Mar 23 12:53:39 PDT 2016
		Wed Mar 23 12:49:49 PDT 2016
			 	*/
			
			
		}
		
	}
	
	// Converts a Date.toString() string to a string representing
	// the date relative to a 24 hour clock
	private String convertTimeTo24HrFormat(String createdAt) {
		int hours, min, sec;
		
		try {
			
			// (11, 19 is whole time)
			hours = Integer.parseInt(createdAt.substring(11, 13));
			
			
		} catch (Exception ex) {
			
		}
	}
	
	public static void main(String[] a) throws TwitterException {
		TimeSeriesUtilities tsu = new TimeSeriesUtilities();
		//writeTSToFile("realDonaldTrump", "src/data/trump_times.txt", tsu.createTwitterInstance(null));
		String test = "Wed Mar 23 12:54:59 PDT 2016";
		System.out.println(test.substring(11, 13));
	}

}
