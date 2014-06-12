package textadventure;
import items.Item;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Player {
	
	ArrayList<Item> inventory = new ArrayList<Item>();
	Location currentLoc;
	
	
	public Location getCurrentLoc() {
		return currentLoc;
	}
	
	// inventory methods 
	public void pickUp(String toGet) {
		if(currentLoc.isInContents(toGet)){
			System.out.println("player trying to pick up " + toGet);
			Thing getting = currentLoc.findInContents(toGet);
			if(getting.getClass().equals(Person.class)){
				Gui.setOutputText(getting.getName() + " would not be very happy if you tried to put them in your inventory.");
				return;
			}
			else {
				if (((Item) getting).pickUp()) {
					inventory.add((Item) getting);
					currentLoc.remove(getting);	
					SwingUtilities.invokeLater(updateInventory);
					Gui.setOutputText("You get " + getting.getName());
				}
				else {
					return;
				}
			}	
		}
		else{
			Gui.setOutputText("Whatever you're trying to pick up, it does not exist.");
		}
	}
	
	public void giveItem(Item i) { // for adding item to invetory that isn't in location. give items to player
		inventory.add(i);
		SwingUtilities.invokeLater(updateInventory);
		
	}
	
	public void use(String toUse) {
		if (isInInventory(toUse)) {
			Item using = (Item) getFromInventory(toUse);
			System.out.println("Using " + using.getName());
			using.use();
		}
		else {
			Gui.setOutputText("You can't use something you don't have.");
		}
	}
	
	public void useOn(String use, String useOn) {
		//TODO make work both ways if different order to args, as with items?
		System.out.println(currentLoc.findInContents(useOn)); 
		if (currentLoc.findInContents(useOn) instanceof Person && isInInventory(use)) {
			Item useItem = (Item) getFromInventory(use);
			Person useOnPerson = (Person) getCurrentLoc().findInContents(useOn);
			System.out.println("Using " + getFromInventory(use).getName() + " on person " + currentLoc.findInContents(useOn));
			useItem.useOn(useOnPerson);
			return;
		}
		
		if (isInInventory(use)) { // this works. use on for items
			if (isInInventory(useOn)) {
				Item useItem = (Item) getFromInventory(use);
				Item useOnItem = (Item) getFromInventory(useOn);
				System.out.println("Using " + useItem.getName() + " on " + useOnItem.getName());
				useItem.useOn(useOnItem);
			}
			else {
				Gui.setOutputText("You can't use that item on an item you don't have");
			}
		}
		else {
			Gui.setOutputText("You can't use something you don't have on another item.");
		}
	}

	public void drop(String toRemove) {
		if(isInInventory(toRemove)){
			Item dropping =(Item) getFromInventory(toRemove);
			inventory.remove(dropping);
			currentLoc.add(dropping);
			SwingUtilities.invokeLater(updateInventory);
			Gui.setOutputText("You have dropped " + dropping.getName());
		}
		else{
			Gui.setOutputText("You can't drop something you don't have.");
		}
	}
	
	// allows for removing an item from inventory as part of another item's use action
	public void removeConsumable(String toUse) {
		if (isInInventory(toUse)) {
			Item using = (Item) getFromInventory(toUse);
			inventory.remove(using);
			SwingUtilities.invokeLater(updateInventory);
		}
		else {
			System.out.println("item not in inventory");
		}
	}
	public boolean isInInventory(String s){ // looks for a thing named s in inventory
		for(Thing i: inventory){
			if(i.getName().equalsIgnoreCase(s)){
				return true;
			}
			
		}
		return false;
	}

	
	public Thing getFromInventory(String s) { // returns object named s
		for (Thing i: inventory) {
			if (i.getName().equalsIgnoreCase(s)) {
			return i;
			} 
		}
		System.out.println("null obj returned"); // testing
		return null;
	}
	
	public void look(String toLook) {
		if(!toLook.isEmpty()){ //if argument was supplied
			if(isInInventory(toLook)){
				Gui.setOutputText(getFromInventory(toLook).getDesc());
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
	
public void talk(String talkTo) {
		if(currentLoc.isInContents(talkTo)){
			if(currentLoc.findInContents(talkTo).getClass().equals(Person.class)){
				Inputter.currentConvo = new Conversation(talkTo);
			}
		}
		else{
			Gui.setOutputText("That person doesn't exist");
		}
	}
	
	public void move(Location l) {
		currentLoc = l;
		l.look();
		return;
		
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
