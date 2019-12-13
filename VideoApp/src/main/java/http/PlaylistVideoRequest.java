package http;

public class PlaylistVideoRequest {

	public String playlist;
	public String videoUrl;
	
	public String getPlaylist() { return playlist; }
	public void setPlaylist(String playlist) { this.playlist = playlist; }
	
	public String getVideoUrl() { return videoUrl; }
	public void setVideoUrl(String video) { this.videoUrl = video; }
	
	public PlaylistVideoRequest() {
		
	}
	
	public PlaylistVideoRequest(String playlist, String video) {
		this.playlist = playlist;
		this.videoUrl = video;
	}
	
	public String toString() {
		return "PlaylistVideo(" + playlist + ", " + videoUrl + ")";
	}
	
}
