import java.util.ArrayList;
import java.util.Scanner;

public class Inputter {
	
	Player player;
	private String inputText;
	
	// not needeed anymore?
	// private String outputText;
	
	
	
	Inputter(Player p) {
		player = p;
	}
	
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	
	/*
	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}
	*/
	
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
		Location toMoveTo;
			if (inputs.size() < 2) {	// check for second argument to move to
				System.out.println("user only typed one word");
				return "You can't move to nowhere.";
			}
	
			System.out.println(inputs.get(1) + " exists: " + player.world.locationExists(inputs.get(1)));
		
			if (!player.world.locationExists(inputs.get(1))) { // if loc doesn't exist
				
				return "You can't go somewhere that doesn't exist.";
			}
			
			else {
				System.out.println("loc exists");
				toMoveTo =  player.world.getLocation(s);
				
				if (!player.currentLoc.isLinked(toMoveTo)) { // locs aren't linked
					System.out.println("locs aren't linked");
					return "You can't go there";
				}
				
				else { // move if okay
					System.out.println("loc " + player.currentLoc.getName() + " is linked to " + toMoveTo.getName());
					
					System.out.println("Moving to " + toMoveTo.getName());
					return player.move(toMoveTo); // move to new loc
				}
			}
			
		}
		
		//KILL
		
		// TODO more actions
		
		
		
		return "test, you're seeing this because the output string couldn't be found. hurray for threads";
	}


}
