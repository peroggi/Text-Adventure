
public class World {
	static Location[] locs; // holds all locations in game world
	
	World() {
		
		// set up each location and items
		Item joint = new Item("joint", "That is a joint.");
		Item testItem = new Item("thing", "does stuff");
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint, testItem});
		
		
		
		// store locations in locs[]
		locs = new Location[10];
		locs[0] = streams;
		
		//TODO finish rest of world
	}
	
	
	
}
