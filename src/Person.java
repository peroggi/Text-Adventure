
public class Person extends Thing{
	String[] dialogue;
	private boolean alive;
	
	Person(String name, String desc, String[] dialogue) {
		this.name = name;
		this.desc = desc;
		this.dialogue = dialogue;
		alive = true;
	}
	
	void get() {
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
	
	boolean useOn(Person person) {
		System.out.println( person.name + " does not want that");
		return false;
	}
	
	void kill() {
		this.alive = false;
		this.desc = "That is " + this.name + ". That person is dead.";
		//other things maybe
	}
	boolean isAlive(){
		return this.alive;
	}
}
