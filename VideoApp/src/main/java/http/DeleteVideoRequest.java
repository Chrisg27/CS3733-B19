package http;

public class DeleteVideoRequest {

	private String clipURL;
	private String associatedText;
	private String speaker;
	private boolean isMarked;
	
	public String getClipURL() { return clipURL; }
	public void setClipURL(String clipURL) { this.clipURL = clipURL; }
	
	public String getAssociatedText() { return associatedText;}
	public void setAssociatedText(String associatedText) {this.associatedText = associatedText;}
	
	public String getSpeaker() { return speaker; }
	public void setSpeaker(String speaker) { this.speaker = speaker; }
	
	public boolean getIsMarked() { return isMarked; }
	public void setIsMarked(boolean isMarked) { this.isMarked = isMarked; }
	
	
	public DeleteVideoRequest() {
	}

	public DeleteVideoRequest(String clipURL, String associatedText, String speaker, boolean isMarked) {
		this.clipURL = clipURL;
		this.associatedText = associatedText;
		this.speaker = speaker;
		this.isMarked = isMarked;
	}
	
	public String toString() {
		return "DeleteVideo(" + clipURL + ", " + associatedText + ", " + speaker + ", " + Boolean.toString(isMarked) + ")";
	}
}
