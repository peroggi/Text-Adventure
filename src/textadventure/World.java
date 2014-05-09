package textadventure;

import items.*;



public class World {
	
	Player player;
	
	World() {
		
		// set starting location and items
		player = new Player();
		
		Joint joint = new Joint();
		System.out.println(joint.getName() + " " + joint.getDesc() + " " + joint.isGetable());
		
		Location streams = new Location("420streams", "This is 420streams.", new Thing[]{joint});
		streams.discover();
		player.currentLoc = streams;
		
		// extra testing location
		Location test = new Location("Test", "This is a strange looking place with nothing in it. Some wannabe programmer must have made it.", new Thing[]{});
		test.discover();
		streams.joinLoc(new Location[]{test});

		
		//TODO finish rest of world
	}
	
}
