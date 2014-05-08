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
		System.out.println(name + " location created."); // for testing
	}
	
	/*public String toString() { again redundant, this inherits getname() from thing
		return name;
	}*/
	
	void look() {
		StringBuilder txt = new StringBuilder(desc + "<br/>You can also see the following things:");
		if(contents.isEmpty()){txt.append("Nothing.");}
		else{
			for(Thing t : contents){
				txt.append(" " + t.getName());
			}
		}
		txt.append("<br/>You also know of the following links from this location:");
		for(Location l : links){
			txt.append(" " + l.getName());
		}
		Gui.setOutputText(txt.toString());
		
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
		return null;
	}
	
	void discover(){
		this.discovered = true;
		}
	boolean isDiscovered() {
		return discovered;
	}	
	List<Location> getLinks(){ //will be called when player attempts to move to a location
		return links;
	}
	/*void testPrintLinks() {
		System.out.print(this.getName() + " is linked to: ");
		for (Location l : links) {
			System.out.print(l.getName() + " " + isLinked(l) + " ");
		}
		System.out.println();
	}*/
	void joinLoc(Location[] locs) {
		for(Location l : locs){
			this.linkLocation(l);
			l.linkLocation(this);
		}
	}
	 void linkLocation(Location loc){
		this.links.add(loc);
		}
	

}
