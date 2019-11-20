package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Library {

	private String name;
	private HashMap<String, Playlist> playlists = new HashMap<String, Playlist>();
	private ArrayList<VideoClip> videos = new ArrayList<VideoClip>();
	private ArrayList<RemoteSite> remoteSites = new ArrayList<RemoteSite>();
	
	/**
	 * Creates a new Library
	 */
	public Library() {}
	
	/**
	 * Creates a new Library with all of its attributes specified
	 * @param name the name of the library
	 * @param playlists the playlists in the library
	 * @param videos the videoclips in the library
	 * @param remoteSites the remotesites in the library
	 */
	public Library(String name, Playlist[] playlists, VideoClip[] videos, RemoteSite[] remoteSites) {}
	
	/**
	 * Returns the name of the Library
	 * @return the name of the Library
	 */
	public String getName() {
		return name;
	}
}
