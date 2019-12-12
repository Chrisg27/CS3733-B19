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
	
	public String toString() {
		return url + " " + character + " " + text;
	}
}
