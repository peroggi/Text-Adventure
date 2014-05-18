package items;

import textadventure.Gui;
import textadventure.World;

public class Lighter extends Item {
	
	public Lighter() {
		name = "lighter";
		desc = "It is a zippo lighter. It has a paw print on it.";
		getable = true;
	}
	
	@Override
	public void useOn(Item i) {
		if (World.getPlayer().isInInventory(i.getName()) && i.getName().equalsIgnoreCase("nazi flag")) {
			Item a = (Item) World.getPlayer().getFromInventory("nazi flag");
			a.useOn(this);	
		}
		else {
			Gui.setOutputText("Those two items don't go together");
		}
	}

}