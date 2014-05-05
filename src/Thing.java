
public class Thing {
	protected String name;
	protected String desc;
	protected boolean getable = false;
	protected boolean talkable = false;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void setDesc(String s) {
		desc = s;
	}
	
	public boolean isGetable() {
		return getable;
	}
	
	public void talk () {
		// so with talkable, would talk be part of thing or part of player?
	}
	
	public boolean isTalkable() {
		return talkable;
	}
	
	public boolean use() {
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
