package model;

public class VideoClip {
	
	private String clipURL;
	private String associatedText;
	private String episode;
	private String speaker;
	private double duration;
	private boolean isMarked;	
	
	/**
	 * Creates a new VideoClip
	 */
	
	VideoClip(){
		
	}
	
	/**
	 * Creates a new VideoClip
	 * @param clipURL the URL of the clips location in a S3 bucket
	 * @param associatedText the words spoken in the clip
	 * @param episode the episode the clip appears in
	 * @param speaker the character who speaks the line
	 * @param duration the length of the clip
	 * @param isMarked whether or not it has been marked for private access only
	 */
	public VideoClip(String clipURL, String associatedText, String episode, String speaker, double duration, boolean isMarked) {
		this.clipURL = clipURL;
		this.associatedText = associatedText;
		this.episode = episode;
		this.speaker = speaker;
		this.duration = duration;
		this.isMarked = isMarked;
	}
	
	/**
	 * Returns the URL of the clip
	 * @return the URL of the clip
	 */
	public String getClipURL() {
		return clipURL;}

	/**
	 * Returns the associated text of the clip
	 * @return the associated text of the clip
	 */
	public String getAssociatedText() {
		return associatedText;
	}

	/**
	 * Returns the episode of the clip
	 * @return the episode of the clip
	 */
	public String getEpisode() {
		return episode;
	}

	/**
	 * Returns the speaker of the clip
	 * @return the speaker of the clip
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * Returns the duration of the clip
	 * @return the duration of the clip
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * Returns whether or not the video has been marked for private access only
	 * @return true if the clip is marked, false if it is not
	 */
	public boolean isMarked() {
		return isMarked;
	}
	
	/**
	 * Marks a video clip for private view only
	 */
	
	public void mark() {
		
	}
	
	/**
	 * Unmarks a video clip
	 */
	public void unmark() {
		
	}
}
