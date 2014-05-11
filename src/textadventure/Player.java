package textadventure;
import items.Item;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Player {
	
	ArrayList<Item> inventory = new ArrayList<Item>();
	Location currentLoc;
	
	// inventory methods 
	public void get(String toGet) {
		if(currentLoc.isInContents(toGet)){
			Thing getting = currentLoc.findInContents(toGet);
			if(getting.getClass().equals(Person.class)){
				Gui.setOutputText(getting.getName() + " would not be very happy if you tried to put them in your inventory.");
				return;
			}
			if(getting.isGetable()){
				inventory.add((Item) getting);
				currentLoc.remove(getting);	
				SwingUtilities.invokeLater(updateInventory);
				Gui.setOutputText("Picked up " + getting.getName());
				return;
			}
			else{
				Gui.setOutputText("You can't pick that up");
			}
		}
		else{
			Gui.setOutputText("Whatever you're trying to pick up, it does not exist.");
		}
	}
	
	public void drop(String toRemove) {
		if(isInInventory(toRemove)){
			Item dropping =(Item) findInInventory(toRemove);
			inventory.remove(dropping);
			currentLoc.add(dropping);
			SwingUtilities.invokeLater(updateInventory);
			Gui.setOutputText("You have dropped " + dropping.getName());
		}
		else{
			Gui.setOutputText("You can't drop something you don't have.");
		}
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
	
	public void look(String toLook) {
		if(!toLook.isEmpty()){//if argument was supplied
			if(isInInventory(toLook)){
				Gui.setOutputText(findInInventory(toLook).getDesc());
			}
			else if(currentLoc.isInContents(toLook)){
				Gui.setOutputText(currentLoc.findInContents(toLook).getDesc());
			}
			else if(currentLoc.getName().equals(toLook)){
				currentLoc.look();
			}
			else{
				Gui.setOutputText("You look at something that exists only in your imagination");//indicates invalid argument
			}
		}
		else{
			currentLoc.look();
		} 
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
