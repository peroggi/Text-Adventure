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

		
		// LOOK TODO print two word items more clearly
		if (inputs.get(0).equalsIgnoreCase("look")) {
			if (inputs.size() > 1) {  // look at item
				inputs.remove(0);
				String nameItemRequested = TextAdventure.listToString(inputs);
				System.out.println(nameItemRequested);
				Item itemToLookAt;
				if (player.currentLoc.isInContents(nameItemRequested)) { // look at item in location
					try{
						itemToLookAt = (Item) player.currentLoc.findInContents(nameItemRequested);
					}
					catch(NullPointerException e){
						System.out.println("NullPointerException:" + e.getCause() + e.getStackTrace());
						return;					
					}
					Gui.setOutputText(itemToLookAt.getDesc());
					return;
				}
				if (player.isInInventory(nameItemRequested)) { // look at item in inventory
					try{
						itemToLookAt = (Item) player.findInInventory(nameItemRequested);
					}
					catch(NullPointerException e){
						System.out.println("NullPointerException:" + e.getCause() + e.getStackTrace());
						return;					
					}
					Gui.setOutputText(itemToLookAt.getDesc());
					return;
				}
			}
			if (inputs.size() < 2) { // single argument, look around
					player.look();
					return;
			}
		}
		// END LOOK

		// GET
		if (inputs.get(0).equalsIgnoreCase("get")) { // checks to see if what user typed is an item in location, gets it
			if (inputs.size() < 2) {
				Gui.setOutputText("You can't get nothing.");
				return;
			}
			inputs.remove(0);
			String nameItemRequested = TextAdventure.listToString(inputs);
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
		// END GET
		
		//USE  
		if (inputs.get(0).equalsIgnoreCase("use")) {
			inputs.remove(0);
			String nameItemRequested = TextAdventure.listToString(inputs);
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
		// END USE
		
		//USE ON TODO


		// DROP
		if (inputs.get(0).equalsIgnoreCase("drop")) { // checks to see if what user typed is an item in inventory, drops it
			inputs.remove(0);
			String nameItemRequested = TextAdventure.listToString(inputs);
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
		// END DROP

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
			Gui.setOutputText("You have tried to move somewhere that doesn't exist.");
			return;
		}
		// END MOVE
		
		//NONE OF THE ABOVE
		Gui.setOutputText((String) TextAdventure.pick(TextAdventure.invalidVerb)); //prints out invalid verb
		return;
	}

}
