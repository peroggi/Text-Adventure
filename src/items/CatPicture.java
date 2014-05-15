package items;

import textadventure.Gui;

public class CatPicture extends Item {
	
	public CatPicture() {
		name = "cat picture";
		desc = "It is a picture of a cat. The cat is doing a dance, and is holding what appears to be a cheeseburger.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText(this.getDesc() + " Maybe someone would want this item?");
	}
}
