package textadventure;

import items.*;


public class World {
	
	protected static Player player;
	
	World() {
		player = new Player();

		Person perogi = new Person("Perogi", "That is Perogi");
		

		Joint joint = new Joint();
		Dildo dildo = new Dildo();
		RedditPost redditPost = new RedditPost();
		NaziFlag naziFlag = new NaziFlag();
		CatPicture catPicture = new CatPicture();
		CatFlair catFlair = new CatFlair();
		FeministPost feministPost = new FeministPost();
		FeministUniform feministUniform = new FeministUniform();
		Key key = new Key();
		Lighter lighter = new Lighter();
		TodayILearnedFlair todayILearnedFlair = new TodayILearnedFlair();
		
	
		//streams
		// set starting location
		Location streams = new Location("shitflix", "This is Shitflix. It is a black room with television screens embedded in the wall at seemingly random places. There are empty bottles of alcohol and drug paraphernalia scattered about.", new Thing[]{joint, dildo, perogi});

		streams.discover();
		player.currentLoc = streams;
		
		// reddit 
		// TODO alternate descriptions for reddit death? either instantiate or have redditdeath set desc
		Location reddit = new Location("Reddit", "You are at Reddit. It is like 4chan used to be, but more pretentious. There are hipsters wandering in and out of edgy subreddits. The air is thick with a feeling of superiority.", new Thing[]{redditPost});
		Location memes = new Location("Memes", "This is a subreddit about memes. A bunch of young adults gather around five year old memes that weren't even that funny originally. The young adults are laughing hysterically.", new Thing[]{});
		Location todayILearned = new Location("Today I Learned", "This is the subreddit for Today I Learned. There are bulletins of unique stories pinned around the walls. People gather around these bulletins and arbitrarily remark on what the bulletin says about humanity and their faith in it.", new Thing[]{});
		Location cats = new Location("Cats", "This is a subreddit for cat pictures. There are lots of pictures of cats. Women between 25 and 35 are wandering around screaming, laughing, and awww'ing at pictures of cats.", new Thing[]{catPicture});
		Location nsaSubreddit = new Location("NSA Subreddit", "This is a secret NSA Subreddit. It is a dismal, gray place filled with cubicles. The only ambience is provided by an old, sluggish ceiling fan slowly spinning around.", new Thing[]{lighter});
		
		// internet backbone
		Location internetBackbone = new Location("Internet Backbone", "You are at the Internet Backbone. This area connects many subnetworks together and acts as an internet hub. There are people hurriedly rushing about, this is a busy place.", new Thing[]{});
		
		// tor
		Location torGate = new Location("Tor Gate", "This is the Tor Gate. It is the entrance to the shadowy Tor Network. It is a network known for anonymity, illegal services, and childporn. In short: it is the ultimate internet blackmarket.", new Thing[]{});
		Location silkRoad = new Location("SilkRoad", "This is the Silkroad. It is a blackmarket on Tor where one can buy drugs, child porn, and hire hitmen. There are shady characters shuffling around everywhere, most are wearing trench coats.", new Thing[]{});
		
		// reich forums
		Location reichForums = new Location("Reich Forums", "This is the Reich Forums. It is a place where Nazis, socialists, and racists gather to talk about things.", new Thing[]{naziFlag});
		
		// blogger
		Location blogger = new Location("Blogger", "This is Blogger. It hosts blogs of many different topics and authors. However, the vast majority of blogs can best be described as self-important.", new Thing[]{});
		Location sheSaid = new Location("She Said", "This is a blog named She Said. It is an extremist feminist blog. The air is thick with estrogen.", new Thing[]{});
		Location sheSaidArchive = new Location("She Said Archive", "This is the archives for the She Said blog. It is full of old and arrogant feminist blog posts.", new Thing[]{key});
		
		// extra testing location
		Location test = new Location("Test", "There's a lot of random shit in here.", new Thing[]{catPicture, naziFlag, catFlair, feministPost, feministUniform, joint, dildo, key, lighter, redditPost, todayILearnedFlair});
		test.discover();
		streams.joinLoc(new Location[]{test});

		// todayILearned for testing redditPost item
		todayILearned.discover();
		streams.joinLoc(new Location[]{todayILearned});

	}
	
	public static Player getPlayer() {
		return player;
	}
	
}
