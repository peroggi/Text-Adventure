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
		Gui.setOutputText(talkee.getChildNodes().item(GREETING).getFirstChild().getNodeValue());
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
		NodeList topicList = talkee.getChildNodes();
		for(int i = 4; i<topicList.getLength(); i++){
			if(topicList.item(i).getNodeName().equalsIgnoreCase(topic)){
				System.out.println("found topic");
				Gui.setOutputText(topicList.item(i).getFirstChild().getNodeValue());
				return;
			}
		}
		System.out.println(topicList.item(0).getNodeValue());
		Gui.setOutputText(topicList.item(INVALID).getFirstChild().getNodeValue());
		
	}
}
