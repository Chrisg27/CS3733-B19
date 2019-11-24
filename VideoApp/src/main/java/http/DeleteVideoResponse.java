package http;

public class DeleteVideoResponse {

	public final String response;
	public final int httpCode;
	public final String error;
	
	public DeleteVideoResponse(String s, int code) {
		this.response = s;
		this.httpCode= code;
		this.error = "";
	}
	
	public DeleteVideoResponse(String s, int code, String error) {
		this.response = s;
		this.httpCode = 200;
		this.error = error;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}
	
}
