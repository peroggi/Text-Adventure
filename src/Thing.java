
public class Thing {
	String name;
	String desc;
	boolean getable = false;
	boolean talkable = false;
	
	String getName(){
		return this.name;
	}
	
	String getDesc() {
		return this.desc;
	}
	
	boolean isGetable() {
		return getable;
	}
	
	void talk () {
		// so with talkable, would talk be part of thing or part of player?
	}
	
	boolean isTalkable() {
		return talkable;
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
