
public class Location extends Thing{
	// what data type to use for links tbd, I'm thinking an array of Location objects?
	Location[] links = null;
	
	Item[] contain = null;
	
	boolean discovered = false;
	
	// i'm guessing your remove method was to remove an item from a location? 
	boolean remove(Object obj) {
		for (Item i : contain) {
			if (i.equals(obj)) {
				i = null;
				return true;
			}
		}
		return false;
	}
	
	boolean look() {
		String txt = this.desc + " You can also see the following things: ";
		
		for (Item i : contain) {
			if (i != null) {
				txt = txt + " " + i;
			}	
		}
	return true;
	}
	
	boolean joinLoc(/*locs*/) {
		// TODO 

	}
}
