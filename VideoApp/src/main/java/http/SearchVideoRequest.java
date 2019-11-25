package http;

public class SearchVideoRequest {

	public String character;
	public String dialogue;
	
	public String getCharacter() { return character; }
	public void setCharacter(String character) { this.character = character; }
	
	public String getDialogue() { return dialogue; }
	public void setDialogue(String dialogue) { this.dialogue = dialogue; }
	
	public SearchVideoRequest() {
	}
	
	public SearchVideoRequest(String character, String dialogue) {
		this.character = character;
		this.dialogue = dialogue;
	}
	
	public String toString() {
		return "SearchVideo(" + character + ", " + dialogue + ")";
	}
	
}
