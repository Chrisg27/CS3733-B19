package http;

public class MarkVideoResponse {

	public final String response;
	public final int httpCode;
	public final String error;
	
	public MarkVideoResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
		this.error = "";
	}
	
	public MarkVideoResponse(String s, int code, String error) {
		this.response = s;
		this.httpCode = 200;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
