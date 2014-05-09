package items;

import textadventure.Gui;

public class Joint extends Item {
	
	public Joint() {
		name = "Joint";
		desc = "It is a marijuana cigarette. It smells like good weed smells.";
	}
	
	public void use() {
		Gui.setOutputText("You smoke just a bit of the joint before putting it out. You get the feeling you might need it later.");
	}
}
