package http;

public class VideoRequest {

public String name;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public VideoRequest() {
	}
	
	public VideoRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Video(" + name + ")";
	}
	
}
