package textadventure;

public class Thing {
	protected String name;
	protected String desc;
	protected boolean getable = false;
	protected boolean talkable = false;
	
	// GETTERS AND SETTERS
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
	
	// OTHER CLASS METHODS
	
	public boolean isGetable() {
		return getable;
	}
	
	public void setGetable() {
		getable = true;
	}
	public void talk () {
		
	}
	
	public boolean isTalkable() {
		return talkable;
	}
	
	public void use() {
		Gui.setOutputText("You can't use that");
	}
	
	void useOn() {
		Gui.setOutputText("Those two items don't go together");
	}
	
	void move(Location startLoc, Location endLoc) {
		startLoc.remove(this);
		endLoc.add(this);
	}

}
