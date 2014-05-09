package items;

import textadventure.Gui;

public class CatPicture extends Item {
	
	public CatPicture() {
		name = "Cat Picture";
		desc = "It is a picture of a cat. The cat is doing a dance.";
		getable = true;
	}
	
	public void use() {
		Gui.setOutputText(this.getDesc() + " Maybe someone would want this item?");
	}
}