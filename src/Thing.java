
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
		
	}
	
	public boolean isTalkable() {
		return talkable;
	}
	
	public void use() {
		Gui.setOutputText("You can't use that.");
	}
	
	public void useOn() {
		Gui.setOutputText("Those two items don't go together");
	}
	
	void move(Location startLoc, Location endLoc) {
		startLoc.remove(this);
		endLoc.add(this);
	}
	
	/*public String toString() { //redundant? getname does same thing
		return this.name;
	}*/
}
