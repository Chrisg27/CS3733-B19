package http;

public class PlaylistVideoRequest {

	public String playlist;
	public String videoURL;
	
	public String getPlaylist() { return playlist; }
	public void setPlaylist(String playlist) { this.playlist = playlist; }
	
	public String getVideoURL() { return videoURL; }
	public void setVideoURL(String video) { this.videoURL = video; }
	
	public PlaylistVideoRequest() {
	}
	
	public PlaylistVideoRequest(String playlist, String video) {
		this.playlist = playlist;
		this.videoURL = video;
	}
	
	public String toString() {
		return "Playlist(" + playlist + ", " + videoURL + ")";
	}
	
}
