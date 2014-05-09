package textadventure;
import items.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class Inputter {
	
	Player player;
	private String inputText;
	
	Inputter() {
		player = TextAdventure.world.player; //aliasing!
	}
	
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	
	// method to parse input, call corresponding class method in player, return output string
	public void checkInput(String s) {
		
		Scanner scanner = new Scanner(s);
		
		ArrayList<String> inputs = new ArrayList<String>();
		// put words user typed into arraylist
		while (scanner.hasNext()) {
			inputs.add(scanner.next());
		}
		scanner.close();
		System.out.println(inputs.toString()); // print input to console for testing
		
		// LOOK
		
		if (inputs.get(0).equalsIgnoreCase("look")) { // just testing with look for now, one word input
			player.look();
			return;
		}
	
		
		// GET
		
		if (inputs.get(0).equalsIgnoreCase("get")) { // checks to see if what user typed is an item in location, gets it
			String nameItemRequested = inputs.get(1);
			Item itemToGet;
			if (player.currentLoc.isInContents(nameItemRequested)) {
				try{
					itemToGet = (Item) player.currentLoc.findInContents(nameItemRequested); // returns object matching string from location
				}
				catch(NullPointerException e){
					System.out.println("NullPointerException:" + e.getCause() + e.getStackTrace());
					return;					
				}
				System.out.println("Getting " + itemToGet.getName()); // print to console to test
				player.get(itemToGet);
				return;
			}
			
			else {
				Gui.setOutputText("There's nothing like that to get");
				return;
			}
		}
		
		//USE  
		if (inputs.get(0).equalsIgnoreCase("use")) {
			String nameItemRequested = inputs.get(1);
			Item itemToUse;
			System.out.println("Something named " + nameItemRequested + " is in inventory: " + player.isInInventory(nameItemRequested));
			if (player.isInInventory(nameItemRequested)) {
				try {
					itemToUse = (Item) player.findInInventory(nameItemRequested);
				}
				catch(NullPointerException e) {
					System.out.println("NullPointerException:" + e.getCause() + e.getStackTrace());
					return;
				}
				System.out.println("Using " + itemToUse.getName());
				player.use(itemToUse);
				return;
			}
			
			else {
				Gui.setOutputText("There's nothing like that to use.");
				return;
			}
		}
		
		//USE ON
		
		
		// DROP
		if (inputs.get(0).equalsIgnoreCase("drop")) { // checks to see if what user typed is an item in inventory, drops it
			String nameItemRequested = inputs.get(1);
			Item itemToDrop;
			
			if (player.isInInventory(nameItemRequested)) {
				itemToDrop = (Item) player.findInInventory(nameItemRequested); // returns object matching string from inventory
				System.out.println("Getting " + itemToDrop.getName()); // print to console to test
				player.drop(itemToDrop);
				return;
			}
			
			else {
				Gui.setOutputText("You don't have that item.");
				return;
			}
		}
		
		
		
		
		//MOVE
		if (inputs.get(0).equalsIgnoreCase("move")) {
			inputs.remove(0);
			String destination = TextAdventure.listToString(inputs);
			for(Location l : player.currentLoc.getLinks()){
				if(destination.equalsIgnoreCase(l.getName()) && l.isDiscovered()){//if linked to currentloc AND discovered, do the move
					player.move(l);
					return;
				}
			}
			//move(inputs);
			Gui.setOutputText("You have tried to move somewhere that doesn't exist.");
			return;
		}
		
		Gui.setOutputText((String) TextAdventure.pick(TextAdventure.invalidVerb)); //prints out invalid verb
		return;
	}
	
	/*changed to new way of writing output, rewrote to be shorter back in the if thing above
	 * 
	 * String move(ArrayList<String> inputs) {	// move is its own method to keep things cleaner
			ArrayList<Location> locs = player.world.getLocs();
			Location toMoveTo = null;
			
			if (inputs.size() < 2) {	// check for second argument to move to
				System.out.println("user only typed one word");
				return "You can't move to nowhere.";
			}
			
			//doesn't work for multi-word locations ie "move internet backbone"
			for (Location l : locs) { // find location object to move to
				if (l.getName().equalsIgnoreCase(inputs.get(1))) {
					toMoveTo = l;
					System.out.println(toMoveTo.getName() + " found");
					break;
				}
			}
					
			if (toMoveTo.equals(null)) { // if location not found
				System.out.println("can't find location object"); 
				return "You can't go somewhere that doesn't exist.";
			}
			
			if (!toMoveTo.isDiscovered()) { // discovered?
				System.out.println(toMoveTo.getName() + " not discovered");
				return "You don't know where that is...yet.";
			}
				
			if(toMoveTo.isLinked(player.currentLoc)) { // linked? 
				System.out.println("moving from " + player.currentLoc.getName() + " to " + toMoveTo.getName());
				return player.move(toMoveTo); // move
			}

			else {
				return "You can't go there from here."; // not linked
			}
		// END MOVE() METHOD
	}	*/
	
	// END INPUTTER CLASS
}
