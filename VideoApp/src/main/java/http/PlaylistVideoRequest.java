package http;

public class PlaylistVideoRequest {

	public String playlist;
	public String video;
	
	public String getPlaylist() { return playlist; }
	public void setPlaylist(String playlist) { this.playlist = playlist; }
	
	public String getVideo() { return video; }
	public void setVideo(String video) { this.video = video; }
	
	public PlaylistVideoRequest() {
	}
	
	public PlaylistVideoRequest(String playlist, String video) {
		this.playlist = playlist;
		this.video = video;
	}
	
	public String toString() {
		return "Playlist(" + playlist + ", " + video + ")";
	}
	
}
