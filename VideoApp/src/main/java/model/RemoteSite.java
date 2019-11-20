package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RemoteSite {

	private String siteURL;
	private HashMap<String, VideoClip> videos = new HashMap<String, VideoClip>();
	
	/**
	 * Creates a new RemoteSite
	 */
	RemoteSite() {
		
	}
	
	/**
	 * Creates a new RemoteSite
	 * @param URL the URL of the site
	 */
	RemoteSite(String URL){
		
	}
	
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
	
	/**
	 * Returns video iterator
	 * @return iterator
	 */
	public Iterator<VideoClip> getVideoIterator() {
		return videos.values().iterator();
	}
}
