package textadventure;

import java.util.ArrayList;
import java.util.List;

import textadventure.Gui;
import textadventure.Thing;
public class Location extends Thing{

	protected List<Location> links = new ArrayList<Location>();
	protected List<Thing> contents = new ArrayList<Thing>();
	protected boolean discovered = false;
	
	public Location(String name, String desc, Thing[] contents){
		this.setName(name);
		this.setDesc(desc);
		for(Thing o : contents){
			this.contents.add(o);
		}
		System.out.println(name + " location created."); // for testing
	}
	
	public void look() {
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
