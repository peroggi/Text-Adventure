package textadventure;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;


public class TextAdventure {

	static World world;

	static Inputter inputter;
	static Gui gui;
	static int currentstage = 0;
	static boolean specialInput = false;
	static String[] invalidVerb = {"You can't do that.", "Shit nigger, what are you even trying to do?", "If I let you do THAT, the game would break.", "Are you trying to cheat?"};

	static String listToString(ArrayList<String> l){//the class defined ArrayList.toString() doesn't do what I want it to
		StringBuilder str = new StringBuilder(l.get(0));
		l.remove(0);
		for(String s : l){
			str.append(" " + s);
		}
		return str.toString();
	}
	
	static Object pick(Object[] array) { //pick a random member of any array, not really type safe so be careful
		Random prng = new Random();
		int randomNum = prng.nextInt(array.length + 1);
		return array[randomNum];
		//TODO figure out generics better and make this type safe maybe?
		//alternatively we could just make it return an int and this would be called like write(invalidVerb[pick(invalidVerb)]; is that even valid?
		//though really we're only going to use this to pick invalidverbs I think, but eh
	}
	
	static void initWorld() {
		world = new World();
		inputter = new Inputter();
	}
	
	public static void main(String[] args) {
		initWorld();
		Conversation.loadDialogue();
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				gui = Gui.makeGui();
			}
		});
	}
	
	public static String saveWorld() {
		try (FileOutputStream fOut = new FileOutputStream("textAdventureSave.bin")) {
			ObjectOutputStream outStream = new ObjectOutputStream(fOut);
			outStream.writeObject(world);
			outStream.close();
			return "Game Saved.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Could not write save file";	
		}
	}
	
	public static String loadWorld() {
		try (FileInputStream fIn = new FileInputStream("textAdventureSave.bin")) {
			ObjectInputStream inStream = new ObjectInputStream(fIn);
			world = (World) inStream.readObject();
			inStream.close();
			return "Game Loaded.";
		} catch (IOException e) {
			e.printStackTrace();
			return "Could not read from save file";
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "Couldn't find World in save file";
			
		}
	}
		
}
