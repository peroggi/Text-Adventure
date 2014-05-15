package items;

import textadventure.*;

public class NaziFlag extends Item {
	public boolean hasFlair = false;
	
	public NaziFlag() {
		name = "nazi flag";
		desc = "That is a Nazi Flag, beloved by members of the Reich Forums.";
		getable = false;
	}
	
	
	public void setGetable() {
		super.setGetable();
	}

	public void get() {
		if (this.getable) {
			if (World.getPlayer().isInInventory(this.name)) {
				Gui.setOutputText("You already have that in your inventory, where are you going to put it?");
				return;
			}
			Gui.setOutputText("You get " + this.name);
			World.getPlayer().get(this);
			return;
		}
		else {
			Gui.setOutputText("You try to get the flag but Sachson puts a hand on your shoulder and shakes his head at you.");
            return;
		}
	}
	
	// TODO make items and locations visible without being a turd
	public void useOn(Item i) {
		if (World.getPlayer().isInInventory("lighter")) {
			if (World.getPlayer().getCurrentLoc().equals(reichForums)) {
				if (World.getPlayer().isInInventory("feminist uniform")) {
					Gui.setOutputText("You quickly light the flag and run off, enraged Nazis are hot on your tail. As soon as you get back into the internet backbone, you ditch your disguise. The Nazis run right past you and into blogger.");
					World.getPlayer().drop(feministUniform);
					World.getPlayer().drop(this);
					// sheSaidDeath(); // TODO she said death event
					return;
				}
				else {
					Gui.setOutputText("If you lit the flag on fire without a disguise, the Nazis would lynch you on the spot.");
					return;
				}
			}
			else {
				Gui.setOutputText("Burning the flag here wouldn't do anything.");
				return;
			}
		}
		else {
			Gui.setOutputText("Those two items don't go together");
			return;
		}
	}
}
	
	