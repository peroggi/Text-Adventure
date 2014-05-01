
public class Thing {
	String name;
	String description;
	boolean getable = false;
	
// why do these methods in omnithing return a boolean?	
	boolean get() {
		System.out.println("You can't pick that up");
		return false;
	}
	
	boolean look() {
		System.out.println(this.description);
		return true;
	}

	boolean talk () {
		System.out.println("If you try to talk to that, people might think you are crazy.");
		return false;
	}
	
	boolean use() {
		System.out.println("I'm not going to let you use that for whatever sick, twisted purpose you have imagined.");
		return false;
	}
	
	boolean useOn() {
		System.out.println("Those two items don't go together");
		return false;
	}
	
	boolean move(Location startLoc, Location endLoc) {
	/*
		if (typeof startloc==="undefined") { 
			startloc = currentloc; 
		}
		startloc.contain.splice(startloc.contain.indexOf(this), 1);
		endloc.contain.push(this);
		return true;}
	/* say what?
	 * 
	 */
		
}
