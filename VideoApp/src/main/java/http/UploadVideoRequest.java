package http;

public class UploadVideoRequest {
	private String clipUrl;
	private String speaker;
	private String associatedText;
	private String base64EncodedVideo;
	
	public String getClipUrl() {return clipUrl;}
	public void setClipUrl(String name) {this.clipUrl = clipUrl;}
	
	public String getSpeaker() { return this.speaker; }
	public void setSpeaker(String speaker) { this.speaker = speaker; }
	
	public String getAssociatedText() { return associatedText; }
	public void setAssociatedText(String associatedText) { this.associatedText = associatedText; }
	
	public String getBase64EncodedVideo() { return this.base64EncodedVideo; }
	public void setBase64EncodedVideo(String s) { this.base64EncodedVideo = s; }
	
	public UploadVideoRequest() {
	}
	
	public UploadVideoRequest (String n, String s, String d, String encoding) {
		this.clipUrl = n;
		this.speaker = s;
		this.associatedText = d;
		this.base64EncodedVideo = encoding;
	}
	
	public String toString() {
		return "UploadVideo(" + clipUrl + ", " + speaker + ", " + associatedText + ", " + base64EncodedVideo + ")";
	}
}
