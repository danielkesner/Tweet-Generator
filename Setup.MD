#Setup instructions

##Setting up Twitter

In order to read from and post to Twitter via the Twitter API, you'll need to associate a Twitter App with your Twitter account. This will generate a set of credentials via OAuth that you can use to authenticate yourself which is necessary to use Twitter4j. If you already have a Twitter account with a set of OAuth keys, you can skip to the Importing to IDE step.

1. Go to https://www.apps.twitter.com and sign in with your Twitter account (or create a new one if you don't have one).
2. Click on the "Create new app" button. Fill in the form with a name, website, and description for your app. Leave the Callback URL field blank for now.
3. After you create a new Twitter app, go back to https://www.apps.twitter.com and open the page for your app. In the Permissions tab, change the Permissions to "Read, Write, and Access Direct Messages", then click Update Settings.
4. Go to the Keys and Access Tokens tab. There are four credentials that you need from here: your Consumer Key, Consumer Secret Key, Access Token, and Access Token Secret. Copy these four strings to a file for the time being.

##Importing project to an IDE

Once you have a set of OAuth keys, we can import this project to an IDE such as Eclipse. To import this project to Eclipse:

1. Switch to the Git perspective by going to Window -> Perspective -> Other and choosing Git. 
2. In the Git perspective, find the button labelled "Clone a Git repository and add the clone to this view". Click that button and in the dialog that comes up, enter the URI for this repository (https://github.com/danielkesner/Tweet-Generator.git) in the URI field then click Next. 
3. You should see a list of branches. Ensure that master is selected and click Next.
4. The default directory name should be your Eclipse workspace followed by the name of the project, i.e. ".../workspace/Tweet-Generator". Click Finish and you should see a new repository appear on the screen.
5. Right-click the repository and click "Import Projects". Make sure "Import using the New Project wizard" is selected and click Finish. 
6. Switch back to the Java perspective and you should see a new project called Tweet-Generator [Tweet-Generator master]. Right-click the project and choose Properties.
7. In the Properties window, choose the Java Build Path section and then navigate to the Libraries tab. Click the "Add External JARs" button. In the ensuing dialog box, navigate to your .../workspace/Tweet-Generator/external_libs/ folder and select both the rita and twitter4j-core-4.0.4 JAR files. Click apply and OK to exit. Eclipse should now show the project without any errors.
8. Navigate to your src/auth/ folder and create a new text file to hold your OAuth keys. For each key, paste it onto its own line in the following order: Consumer Key, Consumer Secret, Access Token, Access Token Secret. The Authenticater class will read the file and store the text from each line into its corresponding variable. 
9. You can now try running the program to ensure everything is properly set up. If there are no issues, you should be able to follow the syntax specified in the README and see similar output.
