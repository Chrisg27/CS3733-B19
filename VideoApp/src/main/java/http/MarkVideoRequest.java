package http;

public class MarkVideoRequest {

	public String url;
	public boolean mark;
	
	public String getURL() { return url; }
	public void setURL(String url) { this.url = url; }
	
	public boolean getMark() { return mark; }
	public void setMark(boolean mark) { this.mark = mark; }
	
	public MarkVideoRequest() {
	}
	
	public MarkVideoRequest(String url, boolean mark) {
		this.url = url;
		this.mark = mark;
	}
	
	public String toString() {
		return "MarkVideo(" + url + ", " + mark + ")";
	}
	
}
