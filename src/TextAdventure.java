
public class TextAdventure {

	int currentstage = 0;
	Location currentLoc;
	boolean specialInput = false;
	String[] invalidVerb = {"You can't do that.", "Shit nigger, what are you even trying to do?", "If I let you do THAT, the game would break.", "Are you trying to cheat?"};
	
	int pick() {
		// TODO 
	
	}
	public static void main(String[] args) {
		Inventory inv = new Inventory();
		inv.showInv();
		Item testItem = new Item("test item", "this is a test item");
		inv.showInv();
	}

}
