import java.util.Random;


public class TextAdventure {

	int currentstage = 0;
	static Inputter inputter; //made static so gui can refer to this
	Location currentLoc;
	boolean specialInput = false;
	String[] invalidVerb = {"You can't do that.", "Shit nigger, what are you even trying to do?", "If I let you do THAT, the game would break.", "Are you trying to cheat?"};

	Object pick(Object[] array) { //pick a random member of any array, not really type safe so be careful
		Random prng = new Random();
		int randomNum = prng.nextInt(array.length + 1);
		return array[randomNum];
		//TODO figure out generics better and make this type safe maybe?
		//alternatively we could just make it return an int and this would be called like write(invalidVerb[pick(invalidVerb)]; is that even valid?
		//though really we're only going to use this to pick invalidverbs I think, but eh
	}
	
	static void initWorld() {
		Player player = new Player();  // set up player
		inputter = new Inputter(player); // set up inputter with instance of player	
		Item joint = new Item("joint", "That is a joint.");
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint});
		//TODO finish
	}
	
	public static void main(String[] args) {
		initWorld();
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Gui.makeGui();
			}
		});
	}

}
