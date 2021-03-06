package items;

import textadventure.Gui;
import textadventure.Person;
import textadventure.World;

public class TodayILearnedFlair extends Item {
	
	public TodayILearnedFlair() {
		name = "todayilearned flair";
		desc = "That's a flair for the todayilearned subreddit. Flairs usually indicate trusted users.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText("This can be used to stamp posts with a flair on the todayilearned subreddit, indicating the post is from a trusted user.");
	}
	
	@Override
	public void useOn(Item i) {
		if (World.getPlayer().isInInventory(i.getName()) && i.getName().equalsIgnoreCase("reddit post")) {
			Item a = (Item) World.getPlayer().getFromInventory("reddit post");
			a.useOn(this);
			}
		else {
			Gui.setOutputText("Those two items don't go together");
		}
	}
	
	@Override
	public void useOn(Person p) {
		if (World.getPlayer().getCurrentLoc().isInContents("old man") && p.getName().equalsIgnoreCase("old man")) {
			Gui.setOutputText("Old Man says: Hehe, yes that will do nicely. Now you just have to <em>write</em> a hilarious post for <em>Reddit</em>, stamp it with that, and post it on the subreddit. That ought to be hilarious enough, hrk.");
		}
		else {
			super.useOn(p);
		}
	}
}