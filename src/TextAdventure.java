
public class TextAdventure {

	int currentstage = 0;
	Location currentLoc;
	boolean specialInput = false;
	String[] invalidVerb = {"You can't do that.", "Shit nigger, what are you even trying to do?", "If I let you do THAT, the game would break.", "Are you trying to cheat?"};
	
	void pick() {
		// TODO 
	
	}
	
	static void initWorld() {
		Player player = new Player();
		Item joint = new Item("joint", "That is a joint.");
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint});
		//TODO finish
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
