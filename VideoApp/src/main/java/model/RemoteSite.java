package model;

public class RemoteSite {

	private String siteURL;
	
	/**
	 * Creates a new RemoteSite
	 */
	public RemoteSite() {
		
	}
	
	/**
	 * Creates a new RemoteSite
	 * @param URL the URL of the site
	 */
	public RemoteSite(String URL){
		this.siteURL = URL;
	}

	/**
	 * Returns the URL of the RemoteSite
	 * @return the siteURL
	 */
	public String getSiteURL() {
		return siteURL;
	}	
	
	/**
	 * Returns whether or not an object is equal to the current RemoteSite
	 * @param o the object to compare to
	 * @return true if they are equal
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(!(o instanceof RemoteSite)) return false;
		RemoteSite remoteSite = (RemoteSite) o;
		return (this.siteURL.equals(remoteSite.siteURL));
	}
}
