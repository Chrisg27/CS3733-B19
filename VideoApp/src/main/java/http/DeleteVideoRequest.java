package http;

public class DeleteVideoRequest {

	public String name;
	public String url;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getURL() { return url; }
	public void setURL(String url) { this.url = url; }
	
	public DeleteVideoRequest() {
	}
	
	public DeleteVideoRequest(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String toString() {
		return "DeleteVideo(" + name + ", " + url + ")";
	}
	
}
