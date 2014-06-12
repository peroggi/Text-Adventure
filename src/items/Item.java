package items;

import textadventure.Gui;
import textadventure.Person;
import textadventure.Thing;

public class Item extends Thing{
	
	public Item() {
	getable = true;
	}
	 
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
	}
	
	public void useOn(Item i) {
		Gui.setOutputText(this.getName() + " and " + i.getName() + " don't do anything when used together.");
	}
	
	public void useOn(Person p) {
		Gui.setOutputText(p.getName() + " doesn't want that item.");
	}
}
