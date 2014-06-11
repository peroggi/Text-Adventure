package textadventure;

import java.io.Serializable;
import java.util.ArrayList;

import items.*;


public class World implements Serializable {
	
	
	private static final long serialVersionUID = 578861841990285434L;
	protected static Player player;
	static ArrayList<Location> undiscoveredLocs = new ArrayList<Location>();
	static Item[] items;
	World() {
		player = new Player();

		Person perogi = new Person("Perogi", "That is Perogi");
		Person toast = new Person("Toast", "That is Toast");
		Person howlie = new Person("Howlie", "");
		Person avgRedditor = new Person("Avg Redditor", "");
		Person philtard = new Person("Philtard", "");
		Person catLady = new Person("Cat lady", "");
		Person oldMan = new Person("Old Man", "");
		Person torGuard = new Person("Tor Guard", "");
		
		// create item instances
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

		//array holding them so they can be accessed by other classes without passing each item. see getItems() method below
		items = new Item[]{joint, dildo, redditPost, naziFlag, catPicture, catFlair, feministPost, feministUniform, key, lighter, todayILearnedFlair};
	
		// starting location
		Location shitflix = new Location("shitflix", "This is Shitflix. It is a black room with television screens embedded in the wall at seemingly random places. There are empty bottles of alcohol and drug paraphernalia scattered about.", new Thing[]{joint, dildo, perogi, toast});
		shitflix.discover();
		player.currentLoc = shitflix;
		
		// reddit 
		// TODO alternate descriptions for reddit death? either instantiate or have redditdeath set desc 
		Location reddit = new Location("Reddit", "You are at Reddit. It is like 4chan used to be, but more pretentious. There are hipsters wandering in and out of edgy subreddits. The air is thick with a feeling of superiority.", new Thing[]{redditPost, avgRedditor});
		Location memes = new Location("Memes", "This is a subreddit about memes. A bunch of young adults gather around five year old memes that weren't even that funny originally. The young adults are laughing hysterically.", new Thing[]{oldMan});
		Location todayILearned = new Location("TodayILearned", "This is the subreddit for Today I Learned. There are bulletins of unique stories pinned around the walls. People gather around these bulletins and arbitrarily remark on what the bulletin says about humanity and their faith in it.", new Thing[]{philtard});
		Location cats = new Location("Cats", "This is a subreddit for cat pictures. There are lots of pictures of cats. Women between 25 and 35 are wandering around screaming, laughing, and awww'ing at pictures of cats.", new Thing[]{catPicture,catLady});
		Location nsaSubreddit = new Location("NSA Subreddit", "This is a secret NSA Subreddit. It is a dismal, gray place filled with cubicles. The only ambience is provided by an old, sluggish ceiling fan slowly spinning around.", new Thing[]{lighter,howlie});
		
		// internet backbone
		Location internetBackbone = new Location("Internet Backbone", "You are at the Internet Backbone. This area connects many subnetworks together and acts as an internet hub. There are people hurriedly rushing about, this is a busy place.", new Thing[]{});
		internetBackbone.discover();
		
		// tor
		Location torGate = new Location("Tor Gate", "This is the Tor Gate. It is the entrance to the shadowy Tor Network. It is a network known for anonymity, illegal services, and childporn. In short: it is the ultimate internet blackmarket.", new Thing[]{torGuard});
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
		shitflix.joinLoc(new Location[]{test});
		

		// join locations
		internetBackbone.joinLoc(new Location[]{shitflix, reddit, torGate, blogger, reichForums});
		blogger.joinLoc(new Location[]{sheSaid, sheSaidArchive});
		blogger.discover();
		reddit.joinLoc(new Location[]{memes, todayILearned, cats, nsaSubreddit});
		torGate.joinLoc(new Location[]{silkRoad});
		torGate.discover();
		 // TODO join all locations that need to be joined. Should they all be joined at start then discovered as story progresses?
	}
	
	public static Player getPlayer() {
		return player;
	}

	// allows other classes to access other items
	public static Item[] getItems() {
		return items;
		
	}
	
	public static void makeItemGetable(String itemName) {
		for (Item i : World.getItems())
			if (i.getName().equalsIgnoreCase("nazi flag")) {
				i.setGetable();
			}
	}

}
