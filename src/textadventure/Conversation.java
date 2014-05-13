package textadventure;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Conversation {
	Node talkee;
	static Document dialogueXML;
	static NodeList people;
	static final int GREETING = 1;
	static final int INVALID = 3;
	
	Conversation(String person){
		for(int i=0; i<people.getLength(); i++){
			if(people.item(i).getNodeName().equalsIgnoreCase(person)){
				this.talkee = people.item(i);
				break;
			}
		}
		String output = talkee.getChildNodes().item(GREETING).getFirstChild().getNodeValue();
		Gui.setOutputText(buildDialogue(output));
	}
	
	static void loadDialogue(){
		try {
			dialogueXML = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("src/textadventure/dialogue.xml");
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		people = dialogueXML.getFirstChild().getChildNodes();
	}
	
	void talk(String topic){
		topic = topic.replaceAll("\\s", "");
		NodeList topicList = talkee.getChildNodes();
		for(int i = 5; i<topicList.getLength(); i+=2){
			if(topicList.item(i).getNodeName().equalsIgnoreCase(topic)){
				String output = topicList.item(i).getFirstChild().getNodeValue();
				Gui.setOutputText(buildDialogue(output));
				return;
			}
		}
		Gui.setOutputText(topicList.item(INVALID).getFirstChild().getNodeValue());
		
	}
	
	String buildDialogue(String dialogue){
		dialogue = dialogue.replaceAll("\\s@", " <em>");
		dialogue = dialogue.replaceAll("/@", "</em>");
		StringBuilder finalDialogue = new StringBuilder(dialogue);
		if(!talkee.getNodeName().equalsIgnoreCase("toast")){
			finalDialogue.insert(0, talkee.getNodeName() + " says:<br/>");
		}
		finalDialogue.append("<br/><br/>Type something to ask " + talkee.getNodeName() + " about, or type cancel to stop talking.");
		return finalDialogue.toString();
	}
}
