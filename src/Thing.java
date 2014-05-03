
public class Thing {
	String name;
	String desc;
	boolean getable = false;
	
	String getName(){
		return this.name;
	}
	
	boolean get() {
		return getable;
		/*so this is what we were kinda talking about. This way it just tells the input/outplut class (whatever we make it)
		 * that getting the object failed, and the input/output class would deal with the output related to that
		 * I think this might be better so that all the output comes from oen sort of area in our code, what you think?
		 */
	}
	
	String look() {
		return this.desc;
	}

	boolean talk () {
		return false; //should we make a boolean talkable? idk if that's more uh conventiony
	}
	
	boolean use() {
		return false;
	}
	
	boolean useOn() {
		System.out.println("Those two items don't go together");
		return false;
	}
	
	void move(Location startLoc, Location endLoc) {
		startLoc.remove(this);
		endLoc.add(this);
	}
}
