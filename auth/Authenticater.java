package auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Authenticater {
	
	private String OAuthConsumerKey;
	private String OAuthConsumerSecret;
	private String OAuthAccessToken;
	private String OAuthAccessTokenSecret;
	
	public static final String DEFAULT_AUTH_PATH = "auth/keys.txt";

	/* Creating a new object populates all keys from file */
	public Authenticater(String pathToAuthFile) {
		authFromFile(pathToAuthFile);
	}
	
	private void authFromFile(String pathToFile) {

		BufferedReader reader;
		String path = "";

		if (pathToFile.equals(DEFAULT_AUTH_PATH)) 
			path = DEFAULT_AUTH_PATH;
		
		else 
			path = pathToFile;
		
		File authFile = new File(path);
		
		// Basic file checking -- try both "src/auth/keys.txt" and "auth/keys.txt"
		// (can sometimes lose src/ package if pushing/pulling from different computers)
		if (! authFile.exists()) {
			System.err.println("ERROR: Unable to locate authentication file with OAuth credentials.\n"
					+ "Attempting to access src/auth/keys.txt instead of auth/keys.txt:");
		
		File authFileTest = new File("src/auth/keys.txt");
		
		if (! authFileTest.exists()) 
			throw new RuntimeException("Unable to find file containing OAuth keys at either src/auth/keys.txt or auth/keys.txt.\n"
					+ "Ensure the paths you entered are consistent with your system and try again.");
		
		else 
			authFile = new File("src/auth/keys.txt");
		}

		try {

			reader = new BufferedReader(new FileReader(authFile));
			OAuthConsumerKey = reader.readLine();
			OAuthConsumerSecret = reader.readLine();
			OAuthAccessToken = reader.readLine();
			OAuthAccessTokenSecret = reader.readLine();

		} catch (Exception ex) {
			System.out.println("Error processing authentication file -- ensure that file format is correct");
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	public String getConsumerKey() {
		return OAuthConsumerKey;
	}
	
	public String getConsumerSecret() {
		return OAuthConsumerSecret;
	}
	
	public String getAccessToken() {
		return OAuthAccessToken;
	}
	
	public String getAccessTokenSecret() {
		return OAuthAccessTokenSecret;
	}

}
