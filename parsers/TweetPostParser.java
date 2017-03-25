package parsers;

/* 
 * @author Daniel Kesner
 * 
 * Implements methods to parse and transform Strings
 * generated with RiTa.   */

public class TweetPostParser {
	
	private static final String wordCharacterRegex = "\\W\\s,&@#!.";
	
	// Given a RiTa-generated sentence, remove all tokens that aren't words
	public static String removeNonAlphaTokens(String sentence) {
		return sentence.replaceAll(wordCharacterRegex, "");
	}
	
	// Removes whitespace preceding/after #, @, %, ','
	public static String attachNonAlphaChars(String sentence) {
		
		// If sentence doesn't contain '#'/'@', return original sentence
		if (! (sentence.contains("@") || sentence.contains("#") 
			|| sentence.contains("%") || sentence.contains(",")))
			return sentence;
		
		// Else remove whitespace separating #/@ and preceding token
		else {
				sentence = sentence.replaceAll("@ ", "@");	// @ realDonaldTrump --> @realDonaldTrump
				sentence = sentence.replaceAll("# ", "#");	// # Hashtag --> #Hashtag
				sentence = sentence.replaceAll(" %", "%");	// 100 % --> 100%
				sentence = sentence.replaceAll(" ,",  ",");	// 33, 000 --> 33,000
				sentence = sentence.replaceAll(", ", ",");	// 33, 000 --> 33,000 (safety)
		}
		return sentence;
	}
}