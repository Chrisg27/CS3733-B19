package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dataBase.VideosDAO;
import model.VideoClip;

public class TestVideos {

	@Test
	public void testCreateGetDelete() {
		
	    try {
	    	VideosDAO videosDAO = new VideosDAO();
	    	
	    	VideoClip clip = new VideoClip("1", "1", "1", true);
	    	assertTrue(videosDAO.addVideoClip(clip));
	    	
	    	VideoClip clip2 = videosDAO.getVideoClip(clip.getClipURL());
	    	assertTrue(clip.equals(clip2));
	    			
	    	assertTrue(videosDAO.deleteVideoClip(clip));
	    	
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testGetAll() {
		
	    try {
	    	VideosDAO videosDAO = new VideosDAO();
	    	VideoClip clip1 = new VideoClip("1", "1", "1", true);
	    	VideoClip clip2 = new VideoClip("2", "2", "2", true);
	    	
	    	
	    	videosDAO.addVideoClip(clip1);
	    	videosDAO.addVideoClip(clip2);
	    	ArrayList<VideoClip> clips = videosDAO.getAllVideos();
	    	
	    	assertTrue(clips.contains(clip1) && clips.contains(clip2));
	    	
	    	videosDAO.deleteVideoClip(clip1);
	    	videosDAO.deleteVideoClip(clip2);
	    	
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}

}
