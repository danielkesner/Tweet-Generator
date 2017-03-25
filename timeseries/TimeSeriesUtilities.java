package timeseries;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//import auth.Authenticater;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import util.TwitterUserUtilities;

public class TimeSeriesUtilities {
	
	public static void writeTimeSeriesToFile(String twitterUsername, String pathToFile, Twitter twitterInstance) throws TwitterException {
		ArrayList<Status> tweets = TwitterUserUtilities.getAllTweetsFromTimeline(twitterInstance, twitterUsername);
		FileWriter writer;
		
		try {
		
		File ts_file = new File(pathToFile);
		if (!ts_file.exists())
			ts_file.createNewFile();
		
		writer = new FileWriter(ts_file);
		
		for (Status status : tweets) {
			Date timePosted = status.getCreatedAt();
			String convertedTime = convertTimeTo24HrFormat(timePosted.toString());
			writer.write(convertedTime);
			writer.write(System.lineSeparator());
		}
		
		} catch (IOException ioe) {
			System.err.println("ERROR initializing FileWriter in writeTSToFile() -- ensure that you passed a valid path");
			System.err.println("The path to the output file you passed is: " + pathToFile);
			ioe.printStackTrace();
			System.exit(-1);
		}
	}
	
	// Converts a Date.toString() string to a string representing
	// the date relative to a 24 hour clock (number of seconds since 00:00:00)
	private static String convertTimeTo24HrFormat(String createdAt) {
		int hours, min, sec;
		int result = 0;
		
		try {
			
			hours = Integer.parseInt(createdAt.substring(11, 13));
			min = Integer.parseInt(createdAt.substring(14, 16));
			sec = Integer.parseInt(createdAt.substring(17, 19));
			
			result = sec + (min * 60) + (hours * 360);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
//	public static void main(String[] a) throws TwitterException {
//		writeTimeSeriesToFile("realDonaldTrump", "data/trump_timeseries.txt", TwitterUserUtilities.createTwitterInstance(Authenticater.DEFAULT_AUTH_PATH));
//	}

}
