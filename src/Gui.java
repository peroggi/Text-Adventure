import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

public class Gui extends JPanel implements ActionListener {
	private TextField input;
	private TextArea output;
	String inputText;
	
	Gui(){
		this.setLayout(new BorderLayout());
		
		input = new TextField(20);
		input.addActionListener(this);
		
		output = new TextArea(5,50);
		output.setEditable(false);
		output.setText("Welcome to the 420streams text adventure! Type and press enter!");
		
		this.add(output, BorderLayout.NORTH);
		this.add(input, BorderLayout.SOUTH);
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
