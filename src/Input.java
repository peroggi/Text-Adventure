import java.util.Scanner;
public class Input {
	
	public static void getInput(String prompt) {
		Scanner kb = new Scanner(System.in);
		System.out.println(prompt);
		String input = kb.nextLine();
		
		String[] inputs = input.split(" ");
		
		for (String i : inputs) {
			//ifs go here. break out of loop after each
			if (inputs[0].equalsIgnoreCase("look")) {
				// TODO look
				
			}
			if (inputs[0].equalsIgnoreCase("get")) {
				// TODO get
				
			}
			if (inputs[0].equalsIgnoreCase("talk")) {
				// TODO get
				
			}
            if (inputs[0].equalsIgnoreCase("use")) {
				if (inputs[1].equalsIgnoreCase("on")) {
					// TODO use on
						
				}
				else {
					// TODO use
				}
			}
            if (inputs[0].equalsIgnoreCase("kill")) {
				//TODO kill
			}
            else {
            	// TODO print something if that command doesn't exist (random error?)
            }
            
			System.out.println(i);
		}

	}

}
