package http;

public class UploadVideoRequest {
	private String clipURL;
	private String speaker;
	private String associatedText;
	private String base64EncodedVideo;
	
	public String getClipURL() {return clipURL;}
	public void setClipURL(String clipURL) {this.clipURL = clipURL;}
	
	public String getSpeaker() { return this.speaker; }
	public void setSpeaker(String speaker) { this.speaker = speaker; }
	
	public String getAssociatedText() { return associatedText; }
	public void setAssociatedText(String dialogue) { this.associatedText = dialogue; }
	
	public String getBase64EncodedVideo() { return this.base64EncodedVideo; }
	public void setBase64EncodedVideo(String s) { this.base64EncodedVideo = s; }
	
	public UploadVideoRequest() {
	}
	
	public UploadVideoRequest (String n, String s, String d, String encoding) {
		this.clipURL = n;
		this.speaker = s;
		this.associatedText = d;
		this.base64EncodedVideo = encoding;
	}
	
	public String toString() {
		return "UploadVideo(" + clipURL + ", " + speaker + ", " + associatedText + ", " + base64EncodedVideo + ")";
	}
}
