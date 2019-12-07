package http;

public class MarkVideoRequest {

	public String clipUrl;
	public boolean mark;
	
	public String getClipUrl() { return clipUrl; }
	public void setClipUrl(String clipUrl) { this.clipUrl = clipUrl; }
	
	public boolean getMark() { return mark; }
	public void setMark(boolean mark) { this.mark = mark; }
	
	public MarkVideoRequest() {
	}
	
	public MarkVideoRequest(String url, boolean mark) {
		this.clipUrl = url;
		this.mark = mark;
	}
	
	public String toString() {
		return "MarkVideo(" + clipUrl + ", " + mark + ")";
	}
	
}
