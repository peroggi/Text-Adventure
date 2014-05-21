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
		DialogueBuilder dialogueBuilder = new DialogueBuilder(talkee.getNodeName());
		String output = talkee.getChildNodes().item(GREETING).getFirstChild().getNodeValue();
		Gui.setOutputText(dialogueBuilder.finalizeDialogue(output));
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
		DialogueBuilder dialogueBuilder = new DialogueBuilder(talkee.getNodeName());
		topic = topic.replaceAll("\\s", "");
		NodeList topicList = talkee.getChildNodes();
		for(int i = 5; i<topicList.getLength(); i+=2){
			if(topicList.item(i).getNodeName().equalsIgnoreCase(topic)){
				Node topicNode = topicList.item(i);
				String output = topicNode.getFirstChild().getNodeValue();
				if(topicNode.hasAttributes()){
					attributeHandler(topicNode, dialogueBuilder);
				}
				Gui.setOutputText(dialogueBuilder.finalizeDialogue(output));
				return;
			}
		}
		Gui.setOutputText(dialogueBuilder.finalizeDialogue(topicList.item(INVALID).getFirstChild().getNodeValue()));
		
	}
	
	private void attributeHandler(Node node, DialogueBuilder dialogueBuilder){
		if(!(node.getAttributes().getNamedItem("link")==null)){
			String discoverLoc = node.getAttributes().getNamedItem("link").getNodeValue();
			for(Location l : World.undiscoveredLocs){
				if(discoverLoc.equalsIgnoreCase(l.getName())){
					l.discover();
					dialogueBuilder.setEventText(String.format("<br/><br/><strong>You have discovered a new link! You can now travel to %s</strong> ", l.getName()));
					break;
				}
			}
		}
	}
	
	private class DialogueBuilder{
		String sayer;
		String says;
		String ask;
		String eventText = "";
		
		DialogueBuilder(String sayer){
			this.sayer = sayer;
			says = String.format("%s says:<br/>", sayer);
			ask = String.format("<br/><br/>Type something to ask %s about, or type cancel to stop talking.", sayer);
		}
		void setEventText(String txt){
			eventText = txt;
		}
		String finalizeDialogue(String dialogue){
			dialogue = dialogue.replaceAll("\\s@", " <em>");
			dialogue = dialogue.replaceAll("/@", "</em>");
			StringBuilder finalDialogue = new StringBuilder(dialogue);
			if(!talkee.getNodeName().equalsIgnoreCase("toast")){
				finalDialogue.insert(0, says);
			}
			finalDialogue.append(ask);
			if(!eventText.isEmpty()){
				finalDialogue.append(eventText);
			}
			return finalDialogue.toString();
		}
	}
}
