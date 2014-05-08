import java.util.ArrayList;

public class Player {
	
	ArrayList<Item> inventory = new ArrayList<Item>();
	Location currentLoc;
	
	// inventory methods 
	String get(Item toAdd) {
		if (toAdd.isGetable()) {
			inventory.add(toAdd);
			currentLoc.remove(toAdd);
			javax.swing.SwingUtilities.invokeLater(updateInventory);
			return "Picked up " + toAdd.getName();
		}
		else {
			return "You can't pick that up.";
		}
	}
	
	String drop(Item toRemove) {
		inventory.remove(toRemove);
		currentLoc.add(toRemove);
		javax.swing.SwingUtilities.invokeLater(updateInventory);
		return "Dropped " + toRemove.getName();
	}
	
	boolean isInInventory(String s){ // looks for a thing named s in inventory
		for(Thing i: inventory){
			if(i.getName().equalsIgnoreCase(s)){
				return true;
			}
			
		}
		return false;
	}

	
	Thing findInInventory(String s) { // returns object named s
		for (Thing i: inventory) {
			if (i.getName().equalsIgnoreCase(s)) {
			return i;
			} 
		}
		System.out.println("null obj returned"); // testing
		return null;
	}
	
	public void look() {
		currentLoc.look(); 
	}
	
	void talk(Person p) {
		// TODO
	}
	
	void use(Item i) {
		i.use();
	}
	
	void useOn(Thing t) {
		// TODO
	}
	
	void move(Location l) {
		currentLoc = l;
		l.look();
		return;
		
		// TODO
	}
	//updates the inventory display to reflect what is in the inventory arraylist. Should be called any time that list is modified
	Runnable updateInventory = new Runnable(){
		public void run(){
			if(inventory.isEmpty()){
				try{TextAdventure.gui.setInv("Inventory:<br/>Empty");}
				catch(Gui.ThreadException e){System.out.println(e.getMessage());}
			}
			else{
				StringBuilder invDisplay = new StringBuilder("Inventory:");
				for(Item i : inventory){
					invDisplay.append("<br/>" + i.getName());
				}
				try{TextAdventure.gui.setInv(invDisplay.toString());}
				catch(Gui.ThreadException e){System.out.println(e.getMessage());}
			}
		}
	};
	
}
