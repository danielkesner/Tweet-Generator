# Tweet Generator

Trains a stochastic model to recognize patterns in speech drawn from any body of text and output randomly-generated sentences that mimic the sentence structure of whatever text the model was trained on. I used this program to create the tweets posted at @Random_Trump, a Twitter account that posts randomly-generated sentences in the same style as @realDonaldTrump's tweets. Please note that this program is <b>not</b> intended to make any kind of political point, I simply needed a popular Twitter account to train the model that powers this program. To see an example of this program in action, you can check out <a href="https://twitter.com/Random_Trump">@Random_Trump's Twitter page here.</a>

This program is built on top of two open-source platforms: <a href="http://twitter4j.org/en/index.html">Twitter4j</a>, a native Java wrapper for the Twitter API that significantly simplifies the calling interface; and <a href="https://rednoise.org/rita/">RiTa</a>, a software toolkit for computational literature. This program mainly functions as a command-line tool that can first create a Markov Chain with RiTa based on a Twitter user's tweets (pulled programmatically from Twitter via Twitter4j), randomly generates a given number of sentences that mimic the sentence structure of the training set, and then posts the randomly-generated sentences back to a Twitter account via Twitter4j. More functionality is being added soon, this project is only several days old and still pretty primitive.

For detailed instructions on importing and using this project, please see Setup.MD. For more detailed information on the model used to generate Tweets, <a href="http://text-analytics101.rxnlp.com/2014/11/what-are-n-grams.html">you can read more about n-grams here</a>. The "n" in "n-factor" and "n-gram" is essentially the size of the set of words that you use to train the model. Smaller values of n (in this context, values around 2 or 3) will produce more randomness and variation in the generated output sentences whereas larger values of n (in this context, values around 4 or 5) will produce more coherent and recognizable sentences. 

##Available commands (more added soon):

`java TwitterMarkovChain --generateFromAllTweets username numSentences <n-factor> <--prompt>`

Generate numSentences sentences using a model trained from  all tweets on username's timeline (with an optional n-factor specified by n-factor). Add the --prompt flag to have the program list out all the sentences it generated and choose which ones you wish to publish to Twitter via standard input.

###Example usage:

`java TwitterMarkovChain --generateFromAllTweets realDonaldTrump 10 4 --prompt`

###Example output:
```Java
[INFO] RiTa.version [1.1.51]
Printing all generated sentences. At the prompt, enter either a single value OR
a comma-separated list of values of sentences to post to Twitter (i.e. '1' or '2,3,6'), or '0' to quit.
1: Made up story by low ratings @Morning_Joe.
2: Taxpayers are paying a fortune for their release.
3: Thank you Franklin! @DanScavino: . @JerryJrFalwell: I was so right!
4: Great evening in San Jose, California- tomorrow at 1: 00pm.
5: It was my great honor.
6: Hillary Clinton should have been prosecuted and should be in jail.
7: We love you! #TrumpPence16 New national poll released.
8: I will think big for our country & its people- how did he get thru system?
1,8
Tweets have been posted.
```
