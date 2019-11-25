package http;

public class RegisterSiteRequest {

	public String url;
	
	public String getURL() { return url; }
	public void setURL(String url) { this.url = url; }
	
	public RegisterSiteRequest() {
	}
	
	public RegisterSiteRequest(String url) {
		this.url = url;
	}
	
	public String toString() {
		return "RegisterSite(" + url + ")";
	}
	
}
