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
		
		if (inputs.get(0).equalsIgnoreCase("look")) { // just testing with look for now, one word input
			return player.look();
		}
		
		if (inputs.get(0).equalsIgnoreCase("inventory")) {
			return player.showInv();
		}
		
		if (inputs.get(0).equalsIgnoreCase("get")) {
			String nameItemRequested = inputs.get(1);
			Item itemToGet;
			if (player.currentLoc.inContents(nameItemRequested)) {
				itemToGet = (Item) player.currentLoc.getThing(nameItemRequested);
				System.out.println("Getting " + itemToGet.getName());
				return player.get(itemToGet);
			}
			
			else {
				return "There's nothing like that to get";
			}
		}
		return "test, you're seeing this because the output string couldn't be found. hurray for threads";
		//commented out for input/thread test
		//return "hurray for threads!";
		// TODO
	}


}
