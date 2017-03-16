package auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Authenticater {
	
	private String OAuthConsumerKey;
	private String OAuthConsumerSecret;
	private String OAuthAccessToken;
	private String OAuthAccessTokenSecret;
	
	private static final String DEFAULT_AUTH_PATH = "src/auth/keys.txt";

	/* Creating a new object populates all keys from file */
	public Authenticater(String pathToAuthFile) {
		authFromFile(pathToAuthFile);
	}
	
	private void authFromFile(String pathToFile) {

		BufferedReader reader;
		String path = "";

		if (pathToFile == null)
			path = DEFAULT_AUTH_PATH;

		File authFile = new File(path);
		if (! authFile.exists())
			throw new RuntimeException("ERROR: Unable to locate authentication file with OAuth credentials.");

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
