import java.util.ArrayList;


public class World {
	ArrayList<Location> locs = new ArrayList<Location>(); // holds all locations in game world
	
	World() {
		
		// set starting location and items
		Item joint = new Item("joint", "That is a joint.");
		Item testItem = new Item("thing", "does stuff");
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint, testItem});
		streams.discover();
		
		// extra testing location
		Location test = new Location("Test", "This is a strange looking place with nothing in it. Some wannabe programmer must have made it.", new Thing[]{});
		test.discover();
		streams.joinLoc(new Location[]{test});
		
		// store locations in locs
		locs.add(streams);
		locs.add(test);
		
		streams.testPrintLinks(); // testing
		testPrintLocs();
		
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
	
	Location getLocation(String s) { // returns location with that name //TODO fix, returning null
			for (Location l : locs) {
				if (s.equalsIgnoreCase(l.getName())) {
					return l;
				}
			}
		System.out.println("null obj returned"); // for testing
		return null;
	}
	
	void testPrintLocs() {
		System.out.print("locs are: ");
		for (Location l : locs) {
			System.out.print(l.getName() + " ");
		}
		System.out.println();
	}
	
	ArrayList<Location> getLocs() {
		return locs;
		
	}
}
