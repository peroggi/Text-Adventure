package textadventure;
import items.Item;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inputter {

	Player player;
	static Conversation currentConvo = null;
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

	public void checkInput(String input) {
		if(currentConvo != null){
			if(input.equalsIgnoreCase("cancel")){
				currentConvo = null;
				player.currentLoc.look();
			}
			else{
				currentConvo.talk(input);
			}
			return;
		}
		System.out.println(input);
		Pattern lookPattern = Pattern.compile("^look(\\s|$)");//^indicates start of line, \s means a space, the first \ escapes the second \
		Pattern getPattern = Pattern.compile("^get(\\s|$)");
		Pattern dropPattern = Pattern.compile("^drop(\\s|$)");
		Pattern usePattern = Pattern.compile("^use(\\s|$)");
		Pattern useOnPattern = Pattern.compile("^useon(\\s|$)"); // i think i got this right for use on?? // TODO use (space) on
		Pattern movePattern = Pattern.compile("^move(\\s|$)");
		Pattern talkPattern = Pattern.compile("^talk(\\s|$)");
		Pattern argumentPattern = Pattern.compile("\\s\\w+($|\\s)");//\\w+ means any amount of word characters(letter/number), $ means end of line
		Matcher argumentMatcher = argumentPattern.matcher(input);
		Matcher trailingSpace = Pattern.compile("\\s$").matcher(input);

		Scanner scanner = new Scanner(input);

		ArrayList<String> inputs = new ArrayList<String>();
		// put words user typed into arraylist
		while (scanner.hasNext()) {
			inputs.add(scanner.next());
		}
		scanner.close();

		//System.out.println(inputs.toString()); // print input to console for testing
		
		// LOOK
		
		if (lookPattern.matcher(input).find()) { // just testing with look for now, one word input
			if(argumentMatcher.find()){//checks if there are arguments (object to look at), only works for one word arguments right now
				player.look(input.substring(argumentMatcher.start()+1, input.length()));//calls player.look with only the argument, not the verb(removes "look" from "look joint")
			}
			else{
				player.look("");	
			}
			return;

		}
		// END LOOK
		
		if(talkPattern.matcher(input).find()){
			if(argumentMatcher.find()){
				player.talk(input.substring(argumentMatcher.start()+1, input.length()));
			}
			else{
				Gui.setOutputText("You talk to yourself for a while, it was a rivetting conversation");
			}
			return;
		}

		// GET/PICK UP
		if (getPattern.matcher(input).find()) { // checks to see if what user typed is an item in location, gets it
			if(argumentMatcher.find()){
				System.out.println("found argument");
				player.pickUp(input.substring(argumentMatcher.start()+1, input.length()));
				return;
			}
			
			// It looks like this is no longer needed
			/*
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
			itemToGet.pickUp();	
			return;
			}
			
			*/
			else{
				Gui.setOutputText("You can't pick up nothing, faggot.");
				return;
			}
		}
		// END GET
		
		//USE  
		if (usePattern.matcher(input).find()) {
			if (argumentMatcher.find()) {
				player.use(input.substring(argumentMatcher.start()+1, input.length()));
				return;
			}
			else {
				Gui.setOutputText("There's nothing like that to use.");
				return;
			}
		}
		// END USE
		
		//USE ON TODO
		if (useOnPattern.matcher(input).find()) {
			if (argumentMatcher.find()) {
				player.use(input.substring(argumentMatcher.start()+1, input.length()));
				return;
			}
			else {
				Gui.setOutputText("There's nothing like that to use with anything.");
			}
		}
		// END USE ON

		// DROP
		if (dropPattern.matcher(input).find()) {
			if(argumentMatcher.find()){
				player.drop(input.substring(argumentMatcher.start()+1, input.length()));
				return;
			}
			else{
				Gui.setOutputText("You have dropped nothing.");
				return;
			}
		}
		// END DROP

		//MOVE
		if (movePattern.matcher(input).find()) {
			if(argumentMatcher.find()){
				String destination = input.substring(argumentMatcher.start()+1, input.length());
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
		// END MOVE
		
		//NONE OF THE ABOVE
		Gui.setOutputText((String) TextAdventure.pick(TextAdventure.invalidVerb)); //prints out invalid verb
		return;
	}
}
