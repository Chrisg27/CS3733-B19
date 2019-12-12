package model;

public class VideoClip {
	
	private String clipURL;
	private String associatedText;
	private String speaker;
	private boolean isMarked;	
	
	/**
	 * Creates a new VideoClip
	 * @param clipURL the URL of the clips location in a S3 bucket
	 * @param associatedText the words spoken in the clip
	 * @param speaker the character who speaks the line
	 * @param isMarked whether or not it has been marked for private access only
	 */
	public VideoClip(String clipURL, String associatedText, String speaker, boolean isMarked) {
		this.clipURL = clipURL;
		this.associatedText = associatedText;
		this.speaker = speaker;
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
	 * Returns the speaker of the clip
	 * @return the speaker of the clip
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * Returns whether or not the video has been marked for private access only
	 * @return true if the clip is marked, false if it is not
	 */
	public boolean isMarked() {
		return isMarked;
	}
	
	/**
	 * Returns whether or not an object is equal to the current VideoClip object
	 * @param o the object to compare it to
	 * @return true if they are equal
	 */
	
	@Override
	public boolean equals(Object o) {
		if(o == this) return true;
		if(!(o instanceof VideoClip)) return false;
		VideoClip videoClip = (VideoClip) o;
		
		if(this.getClipURL().equals(videoClip.getClipURL()) &&
	    			this.getAssociatedText().equals(videoClip.getAssociatedText()) &&
	    			this.getSpeaker().equals(videoClip.getSpeaker()) &&
	    			this.isMarked() == videoClip.isMarked()) return true;
		
		else return false;
	}
}
