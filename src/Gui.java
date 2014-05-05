import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JPanel implements ActionListener {
	protected TextField input;
	protected TextArea output;
	
	Gui(){
		this.setLayout(new BorderLayout());
		
		input = new TextField(20);
		/*from what I understand this makes it so that when enter is pressed on the textfield, it calls this'
		 * actionPerformed() method. It basically makes this (meaning a gui object) listen for actions on the textfield
		 */
		
		input.addActionListener(this);
		
		output = new TextArea(5,50);
		output.setEditable(false);
		output.setText("Welcome to the 420streams text adventure! Type and press enter!");
		
		this.add(output, BorderLayout.NORTH);
		this.add(input, BorderLayout.SOUTH);
	}
	
	
	public void actionPerformed(ActionEvent evt){
		String text = input.getText(); //gets the text that was in the textfield when enter was pressed
		input.setText(""); //erases the text that was in the field
		output.setText("I can see that you've typed something, but I don't know what to do with that text!");
		
		//do stuff with text		
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				Input.getInput("test"); // like this? txt isn't in scope, here, though
			}
		});
	}
	
	static void makeGui(){
		JFrame frame = new JFrame("420streams Text Adventure");
		frame.getContentPane().add(new Gui());
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
