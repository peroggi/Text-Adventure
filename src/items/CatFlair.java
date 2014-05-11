package items;

import textadventure.Gui;

public class CatFlair extends Item {
	
	public CatFlair() {
		name = "cat flair";
		desc = "That's a flair for the Cats subreddit. Flairs usually indicate trusted users.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText("This can be used to stamp posts with a flair on the Cats subreddit, indicating the post is from a trusted user.");
	}
}
