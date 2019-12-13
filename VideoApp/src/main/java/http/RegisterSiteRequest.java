	package http;

public class RegisterSiteRequest {

	public String url;
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public RegisterSiteRequest() {
		
	}
	
	public RegisterSiteRequest(String url) {
		this.url = url;
	}
	
	public String toString() {
		return "RegisterSite(" + url + ")";
	}
	
}
