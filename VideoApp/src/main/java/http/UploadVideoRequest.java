package http;

public class UploadVideoRequest {
	public String speaker;
	public String dialogue;
	public String base64EncodedVideo;
	
	public String getSpeaker() { return this.speaker; }
	public void setSpeaker(String speaker) { this.speaker = speaker; }
	
	public String getDialogue() { return this.dialogue; }
	public void setDialogue(String dialogue) { this.dialogue = dialogue; }
	
	public String getBase64EncodedVideo() { return this.base64EncodedVideo; }
	public void setBase64EncodedVideo(String s) { this.base64EncodedVideo = s; }
	
	public UploadVideoRequest() {
	}
	
	public UploadVideoRequest (String s, String d, String encoding) {
		this.speaker = s;
		this.dialogue = d;
		this.base64EncodedVideo = encoding;
	}
	
	public String toString() {
		return "UploadVideo(" + speaker + "," + dialogue + "," + base64EncodedVideo + ")";
	}
}
