package http;

public class DeleteVideoRequest {

	private String clipUrl;
	
	public String getClipURL() { return clipUrl; }
	public void setClipURL(String clipURL) { this.clipUrl = clipUrl; }
	
	public DeleteVideoRequest() {
	}

	public DeleteVideoRequest(String clipUrl) {
		this.clipUrl = clipUrl;
	}
	
	public String toString() {
		return "DeleteVideo(" + clipUrl + ")";
	}
}
