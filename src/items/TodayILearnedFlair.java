package items;

import textadventure.Gui;

public class TodayILearnedFlair extends Item {
	
	public TodayILearnedFlair() {
		name = "todayilearned flair";
		desc = "That's a flair for the todayilearned subreddit. Flairs usually indicate trusted users.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText("This can be used to stamp posts with a flair on the todayilearned subreddit, indicating the post is from a trusted user.");
	}
}
