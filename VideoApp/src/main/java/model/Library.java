package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Library {

	private String name;
	private HashMap<String, Playlist> playlists = new HashMap<String, Playlist>();
	private HashMap<String, VideoClip> videos = new HashMap<String, VideoClip>();
	private HashMap<String, RemoteSite> remoteSites = new HashMap<String, RemoteSite>();

	/**
	 * Creates a new Library
	 */
	public Library() {

	}
	
	/**
	 * Creates a new Library with all of its attributes specified
	 * @param name the name of the library
	 * @param playlists the playlists in the library
	 * @param videos the videoclips in the library
	 * @param remoteSites the remotesites in the library
	 */
	public Library(String name){

	}

	/**
	 * Returns the name of the Library
	 * @return the name of the Library
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a specific playlist
	 * @param playlistName
	 * @return specified playlist
	 */
	public Playlist getPlaylist(String playlistName) {
		return playlists.get(playlistName);
	}
	
	/**
	 * Creates a new playlist
	 * @param name
	 */
	public void createPlaylist(String name) {

	}
	
	/**
	 * Deletes a playlist
	 * @param name
	 */
	public void deletePlaylist(String name) {

	}
	
	/**
	 * Uploads a new video clip
	 * @param video
	 */
	public void uploadVideo(VideoClip video) {

	}

	/**
	 * Deletes a video clip
	 * @param video
	 */
	public void deleteVideo(VideoClip video) {

	}
	
	/**
	 * Returns video iterator
	 * @return iterator
	 */
	public Iterator<VideoClip> getVideoIterator() {
		return videos.values().iterator();
	}
	
	/**
	 * Returns playlist iterator
	 * @return iterator
	 */
	public Iterator<Playlist> getPlaylistIterator() {
		return playlists.values().iterator();
	}	
	
	/**
	 * Returns remotesite iterator
	 * @return iterator
	 */
	public Iterator<RemoteSite> getRemoteSiteIterator() {
		return remoteSites.values().iterator();
	}
}
