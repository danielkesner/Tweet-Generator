package parsers;

public class ArgumentParser {
	
	public static final int DEFAULT_N_FACTOR = 3;
	public static final int DEFAULT_NUM_SENTENCES = 10;
	
	// Returns n-factor argument from list, or -1 if not found
	public static int getNFactor(String[] args) {
		// Find location of -nfac switch, return element directly after
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-nfac"))
				return Integer.parseInt(args[i+1]);
		}
		return DEFAULT_N_FACTOR;
	}

	// Returns Twitter username, null if not found
	public static String getTwitterUsername(String[] args) {
		// Find location of -nfac switch, return element directly after
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-u"))
				return args[i+1];
		}
		return null;
	}
	
	// Returns the number of sentences for RiTa to generate
	public static int getNumSentences(String[] args) {
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("-n"))
				return Integer.parseInt(args[i+1]);
		}
		return DEFAULT_NUM_SENTENCES;
	}
	
	public static boolean determineIfPrompt(String[] args) {
		for (int i = 1; i < args.length; i++) {
			if (args[i].equals("--prompt"))
				return true;
		}
		return false;
	}

}
