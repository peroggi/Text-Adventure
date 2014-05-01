
public class Person {
	String name;
	String description;
	String[] dialogue;
	boolean alive;
	
	Person(String name, String description, String[] dialogue) {
		this.name = name;
		this.description = description;
		this.dialogue = dialogue;
		this.alive = true;
	}
	
	void get() {
		// TODO get
	}
	 
	void talk() {
		// TODO talk
	}
	
	void use() {
		// TODO use
		//
	}
	
	void useOn() {
		//TODO use on
	}
	
	void kill() {
		this.alive = false;
		this.desc = "That is " + this.name + ". That person is dead."; //is concatenating strings this way correct?
		return true;
		//other things maybe
	}
	boolean isAlive(){
		return this.alive;
	}
}
