package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Playlist {

	private String name;
	private HashMap<String, VideoClip> videos = new HashMap<String, VideoClip>();
	private double duration;
	
	/**
	 * Creates a new Playlist
	 */
	Playlist() {
		
	}
	
	/**
	 * Creates a new Playlist
	 * @param name the name of the playlist
	 * @param videos the videos in the playlist
	 * @param duration the total time of all the clips in the playlist
	 */
	public Playlist(String name, VideoClip[] videos, double duration) {}

	/**
	 * Returns the name of the Playlist
	 * @return the name of the Playlist
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the duration of the Playlist
	 * @return the duration of the PlayList
	 */
	public double getDuration() {
		return duration;
	}
	
	/**
	 * Appends a video to a Playlist
	 * @param video
	 */
	public void addVideo(VideoClip video) {
		
	}
	
	/**
	 * Removes a video from a Playlist
	 * @param video
	 */
	public void removeVideo(VideoClip video) {
		
	}	
	
	/**
	 * Returns video iterator
	 * @return iterator
	 */
	public Iterator<VideoClip> getVideoIterator() {
		return videos.values().iterator();
	}
	
}
