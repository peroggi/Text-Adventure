package items;

import textadventure.*;

public class RedditPost extends Item {
	public boolean hasFlair = false;
	
	public RedditPost() {
		name = "reddit post";
		desc = "It's an inflammatory Reddit post that you wrote. It asserts that Reddit is no different from a Chan.";
		getable = true;
	}
	
	public void use() { // TODO shit that happens
		
		if (hasFlair && World.getPlayer().getCurrentLoc().equals(todayILearned)) {
			System.out.println("reddit death");
			//redditdeath(); // TODO redditdeath
			return;
		}
		if (!hasFlair && World.getPlayer().getCurrentLoc().equals(todayILearned)) {
			Gui.setOutputText("No one will believe this outrageous post unless it has a flair.");
			return;
		}
		Gui.setOutputText("You can't post that here.");
		return;
	}
	
	// TODO fix other shit first
	public void useOn() {
		
	}
}
/*
	// USEON TODO
    redditpostITEM.useon = function (user) {
        if (user == todayilearnedflairITEM) {
            writeOut("You stamp the post with your flair. People will now assume a trusted user composed this post.");
            this.flaired = true;
            return true;
        }
        if (user == catflairITEM) {
            writeOut("Using that flair on this post wouldn't accomplish anything");
            return false;
        }
        writeOut("Those two items don't go together");
        return false;
    };
*/