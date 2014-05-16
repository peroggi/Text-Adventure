package items;

import textadventure.*;

public class FeministPost extends Item {
	
	public FeministPost() {
		name = "feminist post";
		desc = "It is a passive-aggressive and inflammatory post written by Lilithium. It targets the members of the Reich Forums.";
		getable = true;
	}
	
	// TODO visibility
	public void use() {
		if (World.getPlayer().getCurrentLoc().getName().equalsIgnoreCase("reich forums")) {
			Gui.setOutputText("While no one is looking, you discretely post the feminist message in a corner of the forums. It takes a while, but once the Reich members notice they flock around it and become infuriated.");
			World.getPlayer().drop(this.name);
			naziFlag.setGetable();

			return;
		}
		else {
			Gui.setOutputText("You can't post that here.");
			return;
		}
	}
}