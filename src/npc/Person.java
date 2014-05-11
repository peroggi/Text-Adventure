package npc;

import textadventure.Gui;
import textadventure.Thing;

public class Person extends Thing{
	private boolean alive;

	
	public Person(String name, String desc, String[] dialogue) {
		this.name = name;
		this.desc = desc;
		alive = true;
	}
	
	public void get() {
		Gui.setOutputText(this.name + " would not be very happy if you tried to put them in your inventory.");
	}
	
	 /*
	boolean talk() {
		// TODO
	}
	
	*/
	
	public void use() {
		Gui.setOutputText("Using people is wrong.");
	}
	
	public void useOn(Person person) {
		Gui.setOutputText( person.name + " does not want that");
	}
	
	public void kill() {
		this.alive = false;
		this.desc = "That is " + this.name + ". That person is dead.";
		//other things maybe
	}
	public boolean isAlive(){
		return this.alive;
	}
}
