package model;

import java.util.ArrayList;

public class RemoteSite {

	private String siteURL;
	private ArrayList<VideoClip> videos = new ArrayList<VideoClip>();
	
	/**
	 * Creates a new RemoteSite
	 * @param siteURL the URL of the site
	 * @param videos the videos in the remote site
	 */
	public RemoteSite(String siteURL, VideoClip[] videos) {}

	/**
	 * Returns the URL of the RemoteSite
	 * @return the siteURL
	 */
	public String getSiteURL() {
		return siteURL;
	}
}
