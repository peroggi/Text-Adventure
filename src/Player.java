import java.util.ArrayList;

public class Player {
	
	World world = new World(); // world player exists in
	Location currentLoc = World.locs[0]; //set to first location
	ArrayList<Item> inventory = new ArrayList<Item>();
	
	// inventory methods 
	String showInv() {
		if (inventory.isEmpty()) {
			return "Nothing in inventory";
		}
		else {
			String invList = "You have: ";
			for (int i = 0; i< inventory.size(); i++) {
				invList += inventory.get(i) + " ";
			}
			return invList;
		}
	}
	
	
	String get(Item toAdd) {
		if (toAdd.isGetable()) {
			inventory.add(toAdd);
			currentLoc.remove(toAdd);
			return "Picked up " + toAdd.getName();
		}
		else {
			return "You can't pick that up.";
		}
	}
	
	String drop(Item toRemove) {
		inventory.remove(toRemove);
		currentLoc.add(toRemove);
		return "Dropped " + toRemove.getName();
	}
	
	public String look() {
		return currentLoc.look(); 
	}
	
	void talk(Person p) {
		// TODO
	}
	
	String use() {
		// TODO
	}
	
	String useOn(Thing t) {
		// TODO
	}
	
	String move(Location l) {
		// TODO
	}
	
}
