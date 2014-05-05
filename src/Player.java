import java.util.ArrayList;

public class Player {
	
	Location currentLoc = null; //set to first location
	ArrayList<Item> inventory = new ArrayList<Item>();
	
	// inventory methods 
	void showInv() {
		if (inventory.isEmpty()) {
			System.out.println("Nothing in inventory");
		}
		else {
			for (int i = 0; i< inventory.size(); i++) {
				System.out.print(inventory.get(i) + ", ");
			}
		}
	}
	
	
	String get(Item toAdd) {
		if (toAdd.isGetable() && currentLoc.inContents(toAdd)) {
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
