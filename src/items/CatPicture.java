package items;

import textadventure.Gui;
import textadventure.Person;
import textadventure.World;

public class CatPicture extends Item {
	
	public CatPicture() {
		name = "cat picture";
		desc = "It is a picture of a cat. The cat is doing a dance, and is holding what appears to be a cheeseburger.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText(this.getDesc() + " Maybe someone would want this item?");
	}

	@Override
	public void useOn(Person p) {
		if (World.getPlayer().getCurrentLoc().isInContents("toast") && p.getName().equalsIgnoreCase("toast")) {
			Gui.setOutputText("You give toast the cat picture, which he accepts. You see a twinge of emotion on his face, he seems happy.\nToast has given you a cat flair.");
			World.getPlayer().giveItem((Item) World.findInContents("cat flair"));
			World.getPlayer().removeConsumable("cat picture");
		}
		else {
			super.useOn(p);
		}
	}
	
}
