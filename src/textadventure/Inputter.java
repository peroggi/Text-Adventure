package textadventure;
import items.Item;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		System.out.println(s);
		Pattern lookPattern = Pattern.compile("^look(\\s|$)");//^indicates start of line, \s means a space, the first \ escapes the second \
		Pattern getPattern = Pattern.compile("^get(\\s|$)");
		Pattern dropPattern = Pattern.compile("^drop(\\s|$)");
		Pattern usePattern = Pattern.compile("^use(\\s|$)");
		Pattern movePattern = Pattern.compile("^move(\\s|$)");
		Pattern argumentPattern = Pattern.compile("\\s\\w+($|\\s)");//\\w+ means any amount of word characters(letter/number), $ means end of line
		Matcher argumentMatcher = argumentPattern.matcher(s);
		Scanner scanner = new Scanner(s);
		
		ArrayList<String> inputs = new ArrayList<String>();
		// put words user typed into arraylist
		while (scanner.hasNext()) {
			inputs.add(scanner.next());
		}
		scanner.close();
		//System.out.println(inputs.toString()); // print input to console for testing
		
		// LOOK
		
		if (lookPattern.matcher(s).find()) { // just testing with look for now, one word input
			if(argumentMatcher.find()){//checks if there are arguments (object to look at), only works for one word arguments right now
				player.look(s.substring(argumentMatcher.start()+1, argumentMatcher.end()));//calls player.look with only the argument, not the verb(removes "look" from "look joint")
			}
			else{
				player.look("");	
			}
			return;
		}
	
		
		// GET
		
		if (getPattern.matcher(s).find()) { // checks to see if what user typed is an item in location, gets it
			if(argumentMatcher.find()){
				player.get(s.substring(argumentMatcher.start()+1, argumentMatcher.end()));
				return;
			}
			else{
				Gui.setOutputText("You can't pick up nothing, faggot.");
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
		if (dropPattern.matcher(s).find()) { // checks to see if what user typed is an item in inventory, drops it
			if(argumentMatcher.find()){
				player.drop(s.substring(argumentMatcher.start()+1, argumentMatcher.end()));
				return;
			}
			else{
				Gui.setOutputText("You have dropped nothing.");
				return;
			}
		}
		
		
		
		
		//MOVE
		if (movePattern.matcher(s).find()) {
			if(argumentMatcher.find()){
				Matcher removeVerb = movePattern.matcher(s);
				removeVerb.find();
				String destination = s.substring(removeVerb.end(), s.length());
				for(Location l : player.currentLoc.getLinks()){
					if(destination.equalsIgnoreCase(l.getName())){
						player.move(l);
						return;
					}
				}
				Gui.setOutputText("Invalid location");
				return;
			}
			else{
				Gui.setOutputText("You shuffled around a little bit");
				return;
			}
		}
		
		Gui.setOutputText((String) TextAdventure.pick(TextAdventure.invalidVerb)); //prints out invalid verb
		return;
	}
	
	// END INPUTTER CLASS
}
