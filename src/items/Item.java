package items;

import textadventure.Gui;
import textadventure.Thing;
import textadventure.World;

public class Item extends Thing{
	
	public Item() {
	getable = true;
	}
	
	public void look() {
		Gui.setOutputText("It's an item. " + this.getDesc());
	}
	public void get(){
		if (this.getable) {
			System.out.println("Getting " + this.getName()); // print to console to test
			World.getPlayer().get(this);
			return;
		}
		else {
			System.out.println("Not gettable");
			Gui.setOutputText("You can't get that.");
			return;
		}
	}
	
	public void use() {
		Gui.setOutputText("Using that by itself doesn't do anything.");
		return;
	}
}
