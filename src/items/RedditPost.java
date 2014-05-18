package items;

import textadventure.*;

public class RedditPost extends Item {
	public boolean hasFlair = false;
	
	public RedditPost() {
		name = "reddit post";
		desc = "It's an inflammatory Reddit post that you wrote. It asserts that Reddit is no different from a Chan.";
		getable = true;
	}
	
	public void use() {
		
		if (hasFlair && (World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("Today I Learned") || World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("test"))) {
			System.out.println("reddit death");
			//redditdeath(); // TODO redditdeath event
			return;
		}
		if (!hasFlair && (World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("Today I Learned") || World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("test"))) {
			Gui.setOutputText("No one will believe this outrageous post unless it has a flair.");
			return;
		}
		Gui.setOutputText("You can't post that here.");
		return;
	}

	@Override
	public void useOn(Item i) {
		if (World.getPlayer().isInInventory(i.getName()) && i.getName().equalsIgnoreCase("todayilearned flair")) {
			this.hasFlair = true;
			World.getPlayer().removeConsumable("todayilearned flair");
			Gui.setOutputText("You stamp the post with your flair. People will now assume a trusted user composed this post.");
		}
		else {
			Gui.setOutputText("Those two items don't go together");
			return;
		}
	}
	
}
/*
	
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
