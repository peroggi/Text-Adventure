
public class Item extends Thing{
	
	private boolean getable = true;
	
	Item(String n, String d) {
		name = n;
		desc = d;
	}
	
	Item(String n, String d, Boolean g) {
		name = n;
		desc = d;
		getable = g;
	}
	
	public boolean isGetable() {
		return getable;
	}
}
