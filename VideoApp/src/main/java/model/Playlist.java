package model;

import java.util.ArrayList;

public class Playlist {

	private String name;
	private ArrayList<VideoClip> videos = new ArrayList<VideoClip>();
	private double duration;
	
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
	
}
