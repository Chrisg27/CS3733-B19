package http;

public class CreatePlaylistRequest {

	public String name;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public CreatePlaylistRequest() {
	}
	
	public CreatePlaylistRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "CreatePlaylist(" + name + ")";
	}
	
}
