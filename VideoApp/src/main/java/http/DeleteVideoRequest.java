package http;

public class DeleteVideoRequest {

	public String url;
	
	public String getURL() { return url; }
	public void setURL(String url) { this.url = url; }
	
	public DeleteVideoRequest() {
	}
	
	public DeleteVideoRequest(String url) {
		this.url = url;
	}
	
	public String toString() {
		return "DeleteVideo(" + url + ")";
	}
	
}
