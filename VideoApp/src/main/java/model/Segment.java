package model;

public class Segment {
	String url;
	String character;
	String text;
	
	public Segment(String url, String character, String text) {
		this.url = url;
		this.character = character;
		this.text = text;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getCharacter() {
		return character;
	}
	
	public String getText() {
		return text;
	}
	
	public String toString() {
		return url + " " + character + " " + text;
	}
}
