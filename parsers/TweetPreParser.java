package parsers;

/* @author Daniel Kesner
 * 
 * Implements methods to parse and transform original tweets
 * sourced directly from a user's Timeline */

public class TweetPreParser {

	private static final String twitterShortlink = "t.co";
	private static final String regex = "https.*?\\s";

	// Removes all URLs (embedded, beginning, ending), "RT"s, and newlines
	public String trimNonWords(String tweet) {
		String trailingURLRemoved = removeTrailingURLs(tweet);
		return trailingURLRemoved.replaceAll("RT ", "").replaceAll(System.lineSeparator(), "").replaceAll(regex, "");
	}

	// True if String contains "RT", "https://", or newline
	public boolean needsToBeTrimmed(String tweet) {
		return (tweet.contains(System.lineSeparator()) || tweet.contains(twitterShortlink) || tweet.substring(0,2).equals("RT"));
	}

	// Given a String ending with a URL, returns that String with URL removed
	// Ex: "Join me live from the @WhiteHouse. https://t.co/LHOs4nAaGl" --> "Join me live from the @WhiteHouse."
	// Returns a String with URL removed iff tweets ends with a URL -- else returns original String
	// Assumption: People aren't going to include the String "htt" in their tweets unless it's a URL
	private String removeTrailingURLs(String tweet) {

		int beginIndex = tweet.lastIndexOf("htt");	// points to the 'h' in 'https'
		int endIndex = beginIndex;
		char[] buf = tweet.toCharArray();
		
		if (beginIndex < 0)
			beginIndex = 0;

		try {

			while (buf[endIndex++] != ' ');

		} catch (ArrayIndexOutOfBoundsException ai) {
			return tweet.substring(0, beginIndex);
		}
		
		return tweet;
	}
}
