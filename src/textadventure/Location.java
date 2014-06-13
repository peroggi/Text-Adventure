package textadventure;

import java.util.ArrayList;
import java.util.List;
public class Location extends Thing{

	protected List<Location> links = new ArrayList<Location>();
	protected List<Thing> contents = new ArrayList<Thing>();
	protected boolean discovered = false;
	
	public Location(String name, String desc, Thing[] contents){
		this.setName(name);
		this.setDesc(desc);
		World.undiscoveredLocs.add(this);
		for(Thing o : contents){
			this.contents.add(o);
		}
	}
	
	public void look() {
		StringBuilder txt = new StringBuilder(desc + "<br/><br/>You can also see the following things:");
		if(contents.isEmpty()){txt.append("Nothing.");}
		else{
			txt.append(" " + contents.get(0).getName());
			for(int i = 1; i<contents.size();i++){
				txt.append(", " + contents.get(i).getName());
			}
			
		}
		txt.append("<br/><br/>You also know of the following links from this location:");
		for(Location l : links){
			if(l.isDiscovered()){
				txt.append(", " + l.getName());
			}
		}
		Gui.setOutputText(txt.toString().replaceFirst(":, ", ": "));
		
	}
	
	public void remove(Thing o) {	// removes a thing from the location
		this.contents.remove(o);
	}
	
	public void add(Thing o){	// adds a thing to the location
		this.contents.add(o);
		}	
	

	public boolean isInContents(String n){ //checks if something named s exists  in contents
		for(Thing i: contents){
			if(i.getName().equalsIgnoreCase(n)){
				return true;
			}
			
		}
		return false;
	}
	
	public Thing findInContents(String s) { // returns the object named s from contents
		for (Thing i: contents) {
			if (i.getName().equalsIgnoreCase(s)) {
				return i;
			} 
		}
		return null;
	}
	
	public void discover(){
		this.discovered = true;
		World.undiscoveredLocs.remove(this);
	}

	public boolean isDiscovered() {
		return discovered;
	}	
	
	public List<Location> getLinks(){ //will be called when player attempts to move to a location
		return links;
	}
	
	/*void testPrintLinks() {
		System.out.print(this.getName() + " is linked to: ");
		for (Location l : links) {
			System.out.print(l.getName() + " " + isLinked(l) + " ");
		}
		System.out.println();
	}*/
	
	public void joinLoc(Location[] locs) {
		for(Location l : locs){
			this.linkLocation(l);
			l.linkLocation(this);
		}
	}
	
	public void linkLocation(Location loc){
		this.links.add(loc);
	}
	

}
