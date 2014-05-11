package items;

import textadventure.Gui;

public class RedditPost extends Item {
	
	public RedditPost() {
		name = "reddit post";
		desc = "It's an inflammatory Reddit post that you wrote. It asserts that Reddit is no different from a Chan.";
		getable = true;
	}
	
	public void use() { // TODO shit that happens
		
	}
}
/*
var redditpostITEM = Item("reddit post", redditpost.desc);
    redditpostITEM.use = function () {
        if (this.flaired && currentloc == todayilearnedAREA) {
            redditdeath();
            return true;
        }
        if (!this.flaired && currentloc == todayilearnedAREA) {
            writeOut("No one will believe this outrageous post unless it has a flair");
            return false;
        }
        writeOut("You can't post that here");
        return false;
    };
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
