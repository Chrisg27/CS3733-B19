package http;

public class PlaylistResponse {

	public final String response;
	public final int httpCode;
	public final String error;
	
	public PlaylistResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
		this.error = "";
	}
	
	public PlaylistResponse(String s, int code, String error) {
		this.response = s;
		this.httpCode = code;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
