package items;

import textadventure.Gui;

public class Dildo extends Item {
	
	public Dildo() {
		name = "dildo";
		desc = "It's a large pink dildo. It both unsettles and excites you.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText("I'm sure you'd like to do that, but you're not allowed.");
	}
}
