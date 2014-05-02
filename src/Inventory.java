import java.util.ArrayList;

public class Inventory {
	ArrayList contents = new ArrayList();
	
	boolean showInv() {
		if (contents.isEmpty()) {
			System.out.println("Nothing in inventory");
			return false;
		}
		else {
			for (int i = 0; i< contents.size(); i++) {
				System.out.print(contents.get(i) + ", ");
			}
			return true;
		}
	}

	// possible way to add items to the array, creates a new array with one more element, adds the item in the new index, and replaces the original array
	boolean addItem(Item toAdd) {
		contents.add(toAdd);
		return true;
	}

	// possible way to handle removing item from inventory array and resizing. find item to be removed, swap it with item at last index and replace array with truncated copy
	boolean removeItem(Item toRemove) {
		contents.remove(toRemove);
		return true;
	}

}