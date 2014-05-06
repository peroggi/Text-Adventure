import java.util.ArrayList;
import java.util.Scanner;

public class Inputter {
	
	Player player;
	private String inputText;
	
	Inputter(Player p) {
		player = p;
	}
	
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	
	// method to parse input, call corresponding class method in player, return output string
	public String checkInput(String s) {
		
		Scanner scanner = new Scanner(s);
		
		ArrayList<String> inputs = new ArrayList<String>();
		// put words user typed into arraylist
		while (scanner.hasNext()) {
			inputs.add(scanner.next());
		}
		System.out.println(inputs.toString()); // print input to console for testing
		
		// LOOK
		
		if (inputs.get(0).equalsIgnoreCase("look")) { // just testing with look for now, one word input
			return player.look();
		}
		
		// SHOW INVENTORY
		
		if (inputs.get(0).equalsIgnoreCase("inventory")) {
			return player.showInv();
		}
		
		// GET
		
		if (inputs.get(0).equalsIgnoreCase("get")) { // checks to see if what user typed is an item in location, gets it
			String nameItemRequested = inputs.get(1);
			Item itemToGet;
			if (player.currentLoc.isInContents(nameItemRequested)) {
				itemToGet = (Item) player.currentLoc.findInContents(nameItemRequested); // returns object matching string from location
				System.out.println("Getting " + itemToGet.getName()); // print to console to test
				return player.get(itemToGet);
			}
			
			else {
				return "There's nothing like that to get";
			}
		}
		
		// DROP
		if (inputs.get(0).equalsIgnoreCase("drop")) { // checks to see if what user typed is an item in inventory, drops it
			String nameItemRequested = inputs.get(1);
			Item itemToDrop;
			
			if (player.isInInventory(nameItemRequested)) {
				itemToDrop = (Item) player.findInInventory(nameItemRequested); // returns object matching string from inventory
				System.out.println("Getting " + itemToDrop.getName()); // print to console to test
				return player.drop(itemToDrop);
			}
			
			else {
				return "There's nothing like that to get";
			}
		}
		
		
		//USE
		
		//USE ON
		
		//MOVE
		if (inputs.get(0).equalsIgnoreCase("move")) {
			return move(inputs);
		}
		
		// KILL
		
		
		return "test, you're seeing this because the output string couldn't be found. hurray for threads";
	}
	
	String move(ArrayList<String> inputs) {	// move is its own method to keep things cleaner
			ArrayList<Location> locs = player.world.getLocs();
			Location toMoveTo = null;
			
			if (inputs.size() < 2) {	// check for second argument to move to
				System.out.println("user only typed one word");
				return "You can't move to nowhere.";
			}
			if (inputs.size() < 2) {	// check for second argument to move to
				System.out.println("user only typed one word");
				return "You can't move to nowhere.";
			}
				
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
	}	
	
	// END INPUTTER CLASS
}
