package http;

public class PlaylistRequest {

	public String name;
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public PlaylistRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Playlist(" + name + ")";
	}
	
}
