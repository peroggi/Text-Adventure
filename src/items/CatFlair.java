package items;

import textadventure.Gui;
import textadventure.Person;
import textadventure.World;

public class CatFlair extends Item {
	
	public CatFlair() {
		name = "cat flair";
		desc = "That's a flair for the Cats subreddit. Flairs usually indicate trusted users.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText("This can be used to stamp posts with a flair on the Cats subreddit, indicating the post is from a trusted user.");
	}
	
	@Override
	public void useOn(Person p) {
		if (World.getPlayer().getCurrentLoc().isInContents("cat lady") && p.getName().equalsIgnoreCase("cat lady")) {
			Gui.setOutputText("Cat Lady says: eeeeeee omg! Thanks so much! Here you can have my flair for todayilearned, I'm so bored of that subreddit! Thaaanks :)\nThe Cat Lady has given you a todayilearned flair.");
			World.getPlayer().giveItem((Item) World.findInContents("todayilearned flair"));
			World.getPlayer().removeConsumable("cat flair");
		}
		else {
			super.useOn(p);
		}
	}
}
