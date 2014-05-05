
public class TextAdventure {

	static Player player;
	static Inputter inputter;
	int currentstage = 0;
	Location currentLoc;
	boolean specialInput = false;
	String[] invalidVerb = {"You can't do that.", "Shit nigger, what are you even trying to do?", "If I let you do THAT, the game would break.", "Are you trying to cheat?"};

	void pick() {
		// TODO 
	
	}
	
	static void initWorld() {
		player = new Player(); // create player which has world object
		inputter = new Inputter(player); // create inputter that has a player object
	}
	
	public static void main(String[] args) {
		initWorld();
		//makes gui in thread safe way using computer magic
		//multithreading scary!
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Gui.makeGui();
			}
		});
	}

}
