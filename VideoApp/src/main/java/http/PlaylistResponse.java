package http;

public class PlaylistResponse {

	public final String response;
	public final int httpCode;
	
	public PlaylistResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
	}
	
	public PlaylistResponse(String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
