public class Inventory (
	Item[] contents = [0];
	
	boolean showInv() {
		if (contents.length < 1) {
			System.out.println("Nothing in inventory");
			return false;
		}
		else {
			for (Item i : contents) {
				System.out.print(i.name + ", ")
			}
			return true;
		}
	}

	// possible way to add items to the array, creates a new array with one more element, adds the item in the new index, and replaces the original array
	boolean addItem(Item toAdd) {
		contents = copyOf(contents, contents.length + 1);
		contents[contents.length-1] = toAdd;
		return true;
	}

	// possible way to handle removing item from inventory array and resizing. find item to be removed, swap it with item at last index and replace array with truncated copy
	boolean removeItem(Item toRemove) {
		int indexOfItem = 0;
		for (int i = 0; i<contents.length, i++) {
			if (contents[i].equals(toRemove) {
				indexOfItem = i;
				break;
			}
		}
		contents[indexOfItem] = contents[contents.length-1];
		contents = copyOf(contents, contents.length-1);
		return true;
	}
)