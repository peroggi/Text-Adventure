import java.util.ArrayList;
import java.util.List;
public class Location extends Thing{

	private List<Location> links = new ArrayList<Location>();
	private List<Thing> contents = new ArrayList<Thing>();
	private boolean discovered = false;
	
	Location(String name, String desc, Thing[] contents){
		this.setName(name);
		this.setDesc(desc);
		for(Thing o : contents){
			this.contents.add(o);
		}
	}
	
	String look() {
		String txt = this.getDesc() + " You can also see the following things: ";
		String itemNames = "";
		
		if (contents.size() != 0) {
			for (int i = 0; i<contents.size(); i++) {
				Thing item = contents.get(i);
				itemNames += item.getName() + " ";
	
			}
			return txt + " " + itemNames;
		}
		else {
			return txt + " " + "Nothing";
		}
		
	}
	
	void remove(Thing o) {	// removes a thing from the location
		this.contents.remove(o);
	}
	void add(Thing o){	// adds a thing to the location
		this.contents.add(o);
		}	
		
	
	boolean isInContents(String n){ //checks if something named s exists  in contents
		for(Thing i: contents){
			if(i.getName().equalsIgnoreCase(n)){
				return true;
			}
			
		}
		return false;
	}

	
	Thing findInContents(String s) { // returns the object named s from contents
		for (Thing i: contents) {
			if (i.getName().equalsIgnoreCase(s)) {
			return i;
			} 
		}
		
		System.out.println("null obj returned"); // for testing
		return null;
	}
	
	void discover(){
		this.discovered = true;
		}
		
	void linkLocation(Location loc){
		this.links.add(loc);
		}
	
	boolean isLinked(Location l){ //will be called when player attempts to move to a location
		for(Location loc : this.links){
			if(loc.equals(l)){
				return true;
			}
		}
	return false;
	}
	
	void joinLoc(Location[] locs) {
		for(Location l : locs){
			this.linkLocation(l);
			l.linkLocation(this);
		}
	}
		
}
