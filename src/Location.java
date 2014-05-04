import java.util.ArrayList;
import java.util.List;
public class Location extends Thing{

	private List<Location> links = new ArrayList<Location>();
	private List<Thing> contents = new ArrayList<Thing>();
	private boolean discovered = false;
	
	Location(String name, String desc, Thing[] contents){
		this.name = name;
		this.desc = desc;
		for(Thing o : contents){
			this.contents.add(o);
		}
	}
	
	void remove(Thing o) {
		this.contents.remove(o);
	}
	void add(Thing o){
		this.contents.add(o);
		}	
		
	boolean isLinked(String txt){ //will be called when player attempts to move to a location
		for(Location l : this.links){
			if(l.getName() == txt){
				return true;
			}
		}
	return false;
	}
	
	boolean inContents(String txt){ 
		for(Thing t: contents){
			if(t.getName() == txt){
				return true;
			}
		}
	return false;
	}
		
	void discover(){
		this.discovered = true;
		}
		
	void linkLocation(Location loc){
		this.links.add(loc);
		}
	
	String look() {
		String txt = this.desc + " You can also see the following things: ";
		
		for (Object i : contents) {
			txt = txt + " " + i;
		}
		return txt;
	}
	
	void joinLoc(Location[] locs) {
		for(Location l : locs){
			this.linkLocation(l);
			l.linkLocation(this);
		}
	}
		
}
