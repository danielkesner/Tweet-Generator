# Twitter/NLP API

This repository contains code that pulls data from Twitter and uses that data for a variety of tasks such as training a model to mimic a user's Tweets and using Time Series models to identify trends in the time of day that users are most likely to post Tweets. This project is still ongoing and new functionality is added on an ongoing basis.

TwitterMarkovChain.java trains a stochastic model to recognize patterns in speech drawn from any body of text and output randomly-generated sentences that mimic the sentence structure of whatever text the model was trained on. I used this program to create the tweets posted at @Random_Trump, a Twitter account that posts randomly-generated sentences in the same style as @realDonaldTrump's tweets. Please note that this program is <b>not</b> intended to make any kind of political point, I simply needed a popular Twitter account to train the model that powers this program. To see an example of this program in action, you can check out <a href="https://twitter.com/Random_Trump">@Random_Trump's Twitter page here.</a> This program is built on top of two open-source platforms: <a href="http://twitter4j.org/en/index.html">Twitter4j</a>, a Java wrapper for the Twitter API that simplifies the calling interface; and <a href="https://rednoise.org/rita/">RiTa</a>, a software toolkit for computational literature. This program mainly functions as a command-line tool that can first create a Markov Chain with RiTa based on a Twitter user's tweets (pulled programmatically from Twitter via Twitter4j), randomly generates a given number of sentences that mimic the sentence structure of the training set, and then posts the randomly-generated sentences back to a Twitter account via Twitter4j. More functionality is being added soon, this project is only several days old and still pretty primitive.

TimeSeriesUtilities.java pulls all Tweets posted by a given user and stores the time of day the tweet was posted internally. These times are formatted and then written to an output file that will be used as input to an R script that will interrupt these times as a Time Series and build an ARIMA model to attempt to analyze them. Development of the R script and associated tools is still ongoing and is not currently included in this repository.

For detailed instructions on importing and using this project, please see Setup.MD. For more detailed information on the model used to generate Tweets, <a href="http://text-analytics101.rxnlp.com/2014/11/what-are-n-grams.html">you can read more about n-grams here</a>. The "n" in "n-factor" and "n-gram" is essentially the size of the set of words that you use to train the model. Smaller values of n (in this context, values around 2 or 3) will produce more randomness and variation in the generated output sentences whereas larger values of n (in this context, values around 4 or 5) will produce more coherent and recognizable sentences. 

## Available commands (more added soon):

`java TwitterMarkovChain --generateFromAllTweets username numSentences <n-factor> <--prompt>`

Generate numSentences sentences using a model trained from  all tweets on username's timeline (with an optional n-factor specified by n-factor). Add the --prompt flag to have the program list out all the sentences it generated and choose which ones you wish to publish to Twitter via standard input.

### Example usage:

`--generateFromAll -u realDonaldTrump -nfac 5 -n 10 --prompt`

### Example output:
```Java
[INFO] RiTa.version [1.1.51]
Printing all generated sentences. At the prompt, enter either a single value OR
a comma-separated list of values of sentences to post to Twitter (i.e. '1' or '2,3,6'), or '0' to quit.
1: I don't watch or do @Morning_Joe anymore.
2: If this is true, does not get much bigger.
3: Why is it that the Fake News rarely reports Ocare is on its last legs and that insurance companies are fleeing for their lives?
4: Register: Our very weak and ineffective Senator, Jeff Flake.
5: They come at you from all sides.
6: More Spending. #BigLeageTruth #DrainTheSwamp #Debates #BigLeagueTruth. @HillaryClinton's tax hikes will CRUSH our economy.
7: I will be interviewed by @oreillyfactor tonight on @FoxNews at 8pm.
3,6
Tweets have been posted.
```
