import java.util.ArrayList;


public class World {
	static ArrayList<Location> locs = new ArrayList<Location>(); // holds all locations in game world
	
	World() {
		
		// set up each location and items
		Item joint = new Item("joint", "That is a joint.");
		Item testItem = new Item("thing", "does stuff");
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint, testItem});
		
		// extra testing location
		Location test = new Location("Test", "This is a strange looking place with nothing in it. Some wannabe programmer must have made it.", new Thing[]{});
		streams.joinLoc(new Location[]{test}); // TODO I think links are broken
		
		// store locations in locs
		locs.add(streams);
		locs.add(test);
		
		streams.testPrintLinks(); // testing
		
		//TODO finish rest of world
	}
	
	boolean locationExists(String s) { // checks a string to see if world has location of that name
		for (Location l : locs) {
			if (l.getName().equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}
	
	Location getLocation(String s) { // returns location with that name
		if (locationExists(s)) {
			for (Location l : locs) {
				if (l.getName().equalsIgnoreCase(s)) {
					return l;
				}
			}
		}
		return null;
	}
	
}
