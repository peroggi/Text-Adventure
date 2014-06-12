package textadventure;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Inputter {

	Player player;
	static Conversation currentConvo = null;
	private String inputText;

	Inputter() {
		player = World.player;
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
		Pattern lookPattern = Pattern.compile("^look(\\s|$)");
		Pattern getPattern = Pattern.compile("^get(\\s|$)");
		Pattern dropPattern = Pattern.compile("^drop(\\s|$)");
		Pattern usePattern = Pattern.compile("^use(\\s|$)");
		Pattern movePattern = Pattern.compile("^move(\\s|$)");
		Pattern talkPattern = Pattern.compile("^talk(\\s|$)");
		Pattern argumentPattern = Pattern.compile("\\s\\w+($|\\s)");
		Pattern twoArgumentPattern = Pattern.compile("\\s(\\w+|\\w+\\s\\w+)\\son\\s(\\w+|\\w+\\s\\w+)($|\\s)"); //makes UseOn work
		Pattern savePattern = Pattern.compile("^save(\\s|$)");
		Pattern loadPattern = Pattern.compile("^load(\\s|$)");
		Matcher argumentMatcher = argumentPattern.matcher(input);
		Matcher twoArgumentMatcher = twoArgumentPattern.matcher(input);

		
		
		// LOOK
		if (lookPattern.matcher(input).find()) {
			if(argumentMatcher.find()){
				player.look(input.substring(argumentMatcher.start()+1, input.length()));
			}
			else{
				player.look("");	
			}
			return;

		}
		// END LOOK
		
		//TALK
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
		if (getPattern.matcher(input).find()) {
			if(argumentMatcher.find()){
				System.out.println("found argument");
				player.pickUp(input.substring(argumentMatcher.start()+1, input.length()));
				return;
			}
			else{
				Gui.setOutputText("You can't pick up nothing, faggot.");
				return;
			}
		}
		// END GET
		
		//USE ON
		if (twoArgumentMatcher.find()) { // checks for two arguments so "use cat picture on joint" should call useOn?
			input = input.substring(twoArgumentMatcher.start()+1, input.length());
			Pattern findOnPattern = Pattern.compile("\\son\\s");
			Matcher findOnMatcher = findOnPattern.matcher(input);
			findOnMatcher.find();
			String firstArg = input.substring(0, findOnMatcher.start()); // item 1
			String secondArg = input.substring(findOnMatcher.end(), input.length()); // item 2
			player.useOn(firstArg, secondArg);
			return;
		}
		// END USE ON
		
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
					if(destination.equalsIgnoreCase(l.getName())&& l.isDiscovered()){
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
		
		// SAVE
		if (savePattern.matcher(input).find()) {
			Gui.setOutputText(TextAdventure.saveWorld());
		}
		// END SAVE
		
		//LOAD
		if (loadPattern.matcher(input).find()) {
			Gui.setOutputText(TextAdventure.loadWorld());
		}
		//END LOAD
		
		//NONE OF THE ABOVE
		Gui.setOutputText((String) TextAdventure.pick(TextAdventure.invalidVerb)); //prints out invalid verb
		return;
	}
}
