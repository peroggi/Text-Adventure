public class Inputter {

	private String inputText;
	private String outputText;
	Player player;
	
	Inputter(Player p) {
		player = p;
	}
	
	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	
	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}
	
	// method to parse input, call corresponding class method in player, set output text. works by GUI calling checkInput then writeOut to return output
	public void checkInput(String s) {
		this.setInputText(s);
		
		if (inputText.equalsIgnoreCase("look")) { // just testing with look for now, one word input
			this.setOutputText(player.look());
		}	
		// TODO
	}


}
