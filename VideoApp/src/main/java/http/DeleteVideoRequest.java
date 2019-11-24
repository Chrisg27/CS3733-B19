package http;

public class DeleteVideoRequest {

	public String name;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public DeleteVideoRequest() {
	}
	
	public DeleteVideoRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Video(" + name + ")";
	}
	
}
