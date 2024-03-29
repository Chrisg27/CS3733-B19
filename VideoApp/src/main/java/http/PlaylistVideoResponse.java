package http;

public class PlaylistVideoResponse {

	public final String response;
	public final int httpCode;
	public final String error;
	
	public PlaylistVideoResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
		this.error = "";
	}
	
	public PlaylistVideoResponse(String s, int code, String error) {
		this.response = s;
		this.httpCode = code;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
