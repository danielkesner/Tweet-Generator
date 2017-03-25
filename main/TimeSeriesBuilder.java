package main;

import auth.Authenticater;
import parsers.ArgumentParser;
import timeseries.TimeSeriesUtilities;
import twitter4j.TwitterException;
import util.TwitterUserUtilities;

public class TimeSeriesBuilder {
	
	public static void printUsageDescription() {
		System.out.println("Usage: java TimeSeriesBuilder --buildts [args]");
		System.out.println("Interpret the time of day at which a Twitter user posts as a time series. Output is saved to a text file.");
		System.out.println("Each entry in the file corresponds to one of the user's tweets and is equivalent to the number of seconds that have passed since 00:00:00 that day.");
		System.out.println("\nREQUIRED arguments:");
		System.out.println("-u \t\t <string>");
		System.out.println("Specify the username of the Twitter account that you want to create a time series for.");
		System.out.println("OPTIONAL arguments:");
		System.out.println("-p \t\t <string>");
		System.out.println("Specify the location of the output file that you want the time series written to. Omit to write to default file at /data/username_timeseries.txt.");
	}
	
	public static void main(String [] args) throws TwitterException {
		
		if (args.length == 0) {
			printUsageDescription();
			System.exit(0);
		}
			
		if (args.length < 3) {
			System.err.println("Invalid number of arguments");
			System.exit(-1);
		}
		
		// Syntax: --buildts -u username -p pathToOutputFile
		String twitterUser = ArgumentParser.getTwitterUsername(args);
		String pathToFile = ArgumentParser.getPathToFile(args);
		
		if (twitterUser == null) {
			System.err.println("Error parsing Twitter username -- doublecheck syntax and try again");
			System.exit(-1);
		}
		
		// Null string is returned from getPathToFile if no -u switch is provided
		if (pathToFile == null) {
			// If user doesn't specify a path, use default path: "data/twitterUser_ts.txt"
			pathToFile = "data/" + twitterUser + "_timeseries.txt";
		}
			
		TimeSeriesUtilities.writeTimeSeriesToFile(twitterUser, pathToFile, 
				TwitterUserUtilities.createTwitterInstance(Authenticater.DEFAULT_AUTH_PATH));
		
		System.out.println("Time Series data successfully written to output file at: " + pathToFile);

	}

}
