import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Gui extends JPanel implements ActionListener {
	private JTextField input;
	private JTextPane output;
	private JTextPane inventory;
	static final String SAFETHREADNAME = "AWT-EventQueue-0";
	static String inputText;
	static String outputText; //the end result of any action should be to modify this value, which will then be printed on the output gui by the event thread
	
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
	
	static void setOutputText(String t){
		outputText = t;
	}
	
	public void actionPerformed(ActionEvent evt){
		inputText = input.getText();
		input.setText(""); 
		InputWorker inputWorker = new InputWorker();
		inputWorker.execute(); //calls doInBackground() on new thread		
	}
	
	static Gui makeGui(){
		JFrame frame = new JFrame("420streams Text Adventure");
		Gui gui = new Gui();
		frame.getContentPane().add(gui);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return gui;
		
	}
	
	private class InputWorker extends SwingWorker<Boolean, Void>{
		
		public Boolean doInBackground(){
			TextAdventure.inputter.checkInput(inputText);
			return true;
			//TODO will return false if thread is interrupted for some reason also need to handle thread being interrupted, if not handled could fuck everything up in a huge way
		}
		
		public void done(){
			output.setText(outputText);
			//TODO handle errors properly, handle possibility of player trying to submit input while previous input still processing
		}
	}
	
	void setOutput(String s) throws ThreadException{
		if(!Thread.currentThread().getName().equals(SAFETHREADNAME)){//throws error if gui modified from invalid thread
			throw new ThreadException();
		}
		output.setText(s);
	}
	
	void setInv(String s) throws ThreadException{
		if(!Thread.currentThread().getName().equals(SAFETHREADNAME)){
			throw new ThreadException();
		}
		inventory.setText(s);
	}
	
	class ThreadException extends Exception{
		String msg = "ThreadException: The GUI has been modified from an invalid thread. This could cause unpredictable behaviour/crashes in the GUI. Modifying thread: ";
		public String getMessage(){
			msg = msg + Thread.currentThread().getName();
			return msg;
		}
	}
}
