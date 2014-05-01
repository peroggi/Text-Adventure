
public class Thing {
	String name;
	String description;
	boolean getable = false;
	
// why do these methods in omnithing return a boolean?	
/*oh well I guess to say whether the action succeeded or failed. If you try to get a thing
and you can't get it, it returns false. If you can and you do pick up the thing, it returns true.*/
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
	/* say what? */
		/*this is for moving things (not the player) to other places. 
		So when you rescue someone it calls person.move() to make them move elsewhere.
		I don't know how this works in java but in js you could have optional arguments, startLoc was
		an optional argument. So the if statement checks to see if startLoc was passed, if it wasn't it 
		assigns it a default value (where the player currently is). The rest of the method just removes
		the thing from wherever it was, and adds it to it's new location.*/
}
