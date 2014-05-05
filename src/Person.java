
public class Person extends Thing{
	String[] dialogue;
	private boolean alive;
	
	Person(String name, String desc, String[] dialogue) {
		this.name = name;
		this.desc = desc;
		this.dialogue = dialogue;
		alive = true;
	}
	
	boolean get() {
		System.out.println(this.name + " would not be very happy if you tried to put them in your inventory.");
		return false;
	}
	 
	boolean talk() {
		// TODO
	}
	
	boolean use() {
		System.out.println("Using people is wrong.");
		return false;
	}
	
	boolean useOn(Person person) {
		System.out.println( person.name + " does not want that");
		return false;
	}
	
	boolean kill() {
		this.alive = false;
		this.desc = "That is " + this.name + ". That person is dead.";
		return true;
		//other things maybe
	}
	boolean isAlive(){
		return this.alive;
	}
}
