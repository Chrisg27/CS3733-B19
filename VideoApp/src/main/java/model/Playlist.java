package model;

import java.util.HashMap;
import java.util.Iterator;

public class Playlist {

	private String name;
	private int numberOfClips;
	private HashMap<String, VideoClip> videos = new HashMap<String, VideoClip>();
	
	/**
	 * Creates a new Playlist
	 */
	public Playlist() {
		
	}
	
	/**
	 * Creates a new Playlist
	 * @param name the name of the playlist
	 */
	public Playlist(String name) {
		this.name = name;
	}
	
	/**
	 * Creates a new Playlist
	 * @param name the name of the playlist
	 * @param videos the videos in the playlist
	 */
	public Playlist(String name, VideoClip[] videos) {
		this.name = name;
		int counter = 0;
		
		for(VideoClip clip : videos) {
			counter++;
			this.videos.put(clip.getClipURL(), clip);
		}
		
		this.numberOfClips = counter;
	}

	/**
	 * Returns the name of the Playlist
	 * @return the name of the Playlist
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the numberOfClips in the Playlist
	 * @return the numberOfClips in the Playlist
	 */
	public int getNumberOfClips() {
		return numberOfClips;
	}
	
	/**
	 * Appends a video to a Playlist
	 * @param video
	 */
	public void addVideo(VideoClip video) {
		videos.put(video.getClipURL(), video);
		numberOfClips++;
	}
	
	/**
	 * Removes a video from a Playlist
	 * @param video
	 */
	public void removeVideo(String clipURL) {
		videos.remove(clipURL);
		numberOfClips--;
	}	
	
	/**
	 * Returns a VideoClip
	 * @param clipURL the clipURL of the VideoClip to return
	 * @return a VideoClip
	 */
	public VideoClip getVideoClip(String clipURL) {
		return videos.get(clipURL);
	}
	
	/**
	 * Returns video iterator
	 * @return iterator
	 */
	public Iterator<VideoClip> getVideoIterator() {
		return videos.values().iterator();
	}
	
	/**
	 * Returns whether an object is equal to the current playlist
	 * @param o the object to compare to
	 * @return true if the two objects are equal
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(!(o instanceof Playlist)) return false;
		Playlist playlist = (Playlist) o;
		return (this.name.equals(playlist.name) && this.videos.equals(playlist.videos));
	}
	
}
