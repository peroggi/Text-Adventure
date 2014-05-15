package items;

import javax.swing.SwingUtilities;

import textadventure.Gui;
import textadventure.Thing;
import textadventure.World;

public class Item extends Thing{
	
	public Item() {
	getable = true;
	}
	

	// TODO change get items 
	public Boolean pickUp(){
		if (this.getable) {
			System.out.println("Get " + this.getName()); // print to console to test
			return true;
		}
		else {
			Gui.setOutputText("You can't pick that up");
			return false;
		}
	}
	
	public void use() {
		Gui.setOutputText("That doesn't do anything.");
		return;
	}
	
	// TODO use on for Item
	public void useOn() {
		
	}
}
