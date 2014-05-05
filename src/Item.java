
public class Item extends Thing{
	private String name;
	private String desc;
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
	
}
