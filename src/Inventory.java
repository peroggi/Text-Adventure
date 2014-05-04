// not using, moved to Player class

import java.util.ArrayList;

public class Inventory {
	ArrayList<Item> contents = new ArrayList<Item>();
	
	void showInv() {
		if (contents.isEmpty()) {
			System.out.println("Nothing in inventory");
		}
		else {
			for (int i = 0; i< contents.size(); i++) {
				System.out.print(contents.get(i) + ", ");
			}
		}
	}

	// possible way to add items to the array, creates a new array with one more element, adds the item in the new index, and replaces the original array
	void addItem(Item toAdd) {
		contents.add(toAdd);

	}

	// possible way to handle removing item from inventory array and resizing. find item to be removed, swap it with item at last index and replace array with truncated copy
	void removeItem(Item toRemove) {
		contents.remove(toRemove);
	}
	
}