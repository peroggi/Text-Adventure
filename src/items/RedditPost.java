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
		
		if (hasFlair && (World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("TodayILearned") || World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("test"))) {
			System.out.println("reddit death");
			redditDeath();
			return;
		}
		if (!hasFlair && (World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("TodayILearned") || World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("test"))) {
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
	
	public void redditDeath() {
		Gui.setOutputText("You post your well written article on the wall of the subreddit. A few people crowd around and observe the post, their eyes going wide as they read. Word spreads quickly and the self-important Redditors begin screaming as their self-image collapses around them. After a few moments, several of them begin to commit suicide.");
        // TODO kill philtard
    	// TODO kill avgredditor
    	// TODO change description for reddit, memes, todayilearned, and cats.
    	// TODO change old man dialogue
    }

}