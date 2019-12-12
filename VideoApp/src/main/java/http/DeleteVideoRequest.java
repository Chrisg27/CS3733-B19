package http;

public class DeleteVideoRequest {

	private String clipUrl;
	
	public String getClipUrl() { return clipUrl; }
	public void setClipUrl(String clipUrl) { this.clipUrl = clipUrl; }

	public DeleteVideoRequest(String clipUrl) {
		this.clipUrl = clipUrl;
	}
	
	public String toString() {
		return "DeleteVideo(" + clipUrl + ")";
	}
}
