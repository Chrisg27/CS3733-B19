package http;

public class VideoResponse {

	public final String response;
	public final int httpCode;
	
	public VideoResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
	}
	
	public VideoResponse(String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
