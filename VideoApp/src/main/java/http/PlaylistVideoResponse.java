package http;

public class PlaylistVideoResponse {

	public final String response;
	public final int httpCode;
	
	public PlaylistVideoResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
	}
	
	public PlaylistVideoResponse(String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
