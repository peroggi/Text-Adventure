import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;
import javax.swing.border.*;
import javax.swing.*;

public class Gui extends JPanel implements ActionListener {
	private JTextField input;
	private JTextPane output;
	private JTextPane inventory;
	String inputText;
	
	Gui(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		input = new JTextField(20);
		input.addActionListener(this);
		
		output = new JTextPane();
		output.setEditable(false);
		output.setContentType("text/html");
		output.setPreferredSize(new Dimension(375,250));
		output.setText("Welcome to the <em>420streams</em> text adventure! Type and press enter!");
		
		inventory = new JTextPane();
		inventory.setContentType("text/html");
		inventory.setText("Inventory:<br/>Empty");
		inventory.setEditable(false);
		inventory.setPreferredSize(new Dimension(150,200));
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 100;
		c.insets = new Insets(5,5,5,5);
		c.weighty = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(output, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.75;
		this.add(input, c);
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		c.insets = new Insets(5,5,5,5);
		c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 1;
		this.add(inventory, c);
	}
	
	
	public void actionPerformed(ActionEvent evt){
		inputText = input.getText(); //gets the text that was in the textfield when enter was pressed
		input.setText(""); //erases the text that was in the textfield
		InputWorker inputWorker = new InputWorker(); //create thread
		inputWorker.execute(); //calls doInBackground() on new thread		
	}
	
	static void makeGui(){
		JFrame frame = new JFrame("420streams Text Adventure");
		frame.getContentPane().add(new Gui());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private class InputWorker extends SwingWorker<String, Void>{
		String textToWrite;
		
		public String doInBackground(){ //everything in here is done on a new thread
			String outputText = TextAdventure.inputter.checkInput(inputText);
			System.out.println(Thread.currentThread().getName());
			return outputText;
		}
		
		public void done(){ //called on the event thread once the worker thread is finished, meaning gui things happen here
			try{ //I think try statements make a new scope, which is annoying. Or am I just doing things wrong?
				//if I declare textToWrite in the try statement no one else knows it exists
			textToWrite = get(); //gets the return value of doInBackground()
			}
			catch(InterruptedException | ExecutionException ignore){}
			System.out.println(Thread.currentThread().getName());
			output.setText(this.textToWrite); //thread safe since gui is being manipulated by event thread only
			//TODO handle errors properly, handle possibility of player trying to submit input while previous input still processing
		}
	}
}
