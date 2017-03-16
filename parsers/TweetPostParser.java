package parsers;

/* 
 * @author Daniel Kesner
 * 
 * Implements methods to parse and transform Strings
 * generated with RiTa.   */

public class TweetPostParser {
	
	private static final String wordCharacterRegex = "\\W\\s,&@#!.";

	// Given a RiTa-generated sentence, remove all tokens that aren't words
	public String removeNonAlphaTokens(String sentence) {
		return sentence.replaceAll(wordCharacterRegex, "");
	}
	
	// Removes whitespace between '#' / '@' and the token immediately after
	// from RiTa-generated sentences, i.e. "@ realDonaldTrump" --> "@realDonaldTrump"
	public String attachHashtagsAndMentions(String sentence) {
		
		// If sentence doesn't contain '#'/'@', return original sentence
		if (! (sentence.contains("@") || sentence.contains("#")))
			return sentence;
		
		// Else remove whitespace separating #/@ and preceding token
		else {
				sentence = sentence.replaceAll("@ ", "@");
				sentence = sentence.replaceAll("# ", "#");
		}
		return sentence;
	}
}