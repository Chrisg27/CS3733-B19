package http;

public class DeletePlaylistRequest {
	
	public String name;
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public DeletePlaylistRequest() {
	}
	
	public DeletePlaylistRequest(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "DeletePlaylist(" + name + ")";
	}
	
}
