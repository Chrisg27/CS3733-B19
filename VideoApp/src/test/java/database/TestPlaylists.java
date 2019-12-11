package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
import dataBase.PlaylistsDAO;
import dataBase.VideosDAO;
import model.Playlist;
import model.VideoClip;

public class TestPlaylists {
	
	@Test
	public void testCreateGetDelete() {
		
	    try {
	    	PlaylistsDAO playlistDAO = new PlaylistsDAO();
	    	VideosDAO videoDAO = new VideosDAO();
	    	VideoClip clip = new VideoClip("1", "1", "1", true);
	    	VideoClip[] clips = {clip};
	    	Playlist playlist = new Playlist("1", clips);
	    	
	    	videoDAO.addVideoClip(clip);
	    	assertTrue(playlistDAO.addEmptyPlaylist(playlist));
	    	assertTrue(playlistDAO.addVideoClipToPlaylist(playlist, clip));
	    	assertTrue(playlist.equals(playlistDAO.getPlaylist(playlist.getName())));
	    	assertTrue(playlistDAO.deletePlaylist(playlist));
	    	videoDAO.deleteVideoClip(clip);
	    	
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testAddRemoveClip() {
		
		try {
	    	PlaylistsDAO playlistDAO = new PlaylistsDAO();
	    	VideosDAO videoDAO = new VideosDAO();
	    	VideoClip clip = new VideoClip("1", "1", "1", true);
	    	VideoClip[] clips = {clip};
	    	Playlist playlist = new Playlist("1", clips);
	    	
	    	videoDAO.addVideoClip(clip);
	    	playlistDAO.addEmptyPlaylist(playlist);
	    	assertTrue(playlistDAO.addVideoClipToPlaylist(playlist, clip));
	    	assertTrue(playlistDAO.deleteVideoClipFromPlaylist(playlist, clip));
	    	assertTrue(playlistDAO.getPlaylist(playlist.getName()).getNumberOfClips() == 0);
	    	playlistDAO.deletePlaylist(playlist);
	    	videoDAO.deleteVideoClip(clip);
	    	
		} catch (Exception e) {
			fail("didn't work:" + e.getMessage());
		}

	}
	
	@Test
	public void testGetAllPlaylists() {
		
		try {
			PlaylistsDAO playlistDAO = new PlaylistsDAO();
			VideosDAO videoDAO = new VideosDAO();
			
			VideoClip clip1 = new VideoClip("1", "1", "1", false);
			VideoClip clip2 = new VideoClip("2", "2", "2", false);
			VideoClip[] clips = {clip1, clip2};
			Playlist playlist = new Playlist("1", clips);
			Playlist playlist2 = new Playlist("2", clips);
			
			playlistDAO.addEmptyPlaylist(playlist);
			playlistDAO.addEmptyPlaylist(playlist2);
			videoDAO.addVideoClip(clip1);
			videoDAO.addVideoClip(clip2);
			playlistDAO.addVideoClipToPlaylist(playlist, clip1);
			playlistDAO.addVideoClipToPlaylist(playlist, clip2);
			playlistDAO.addVideoClipToPlaylist(playlist2, clip1);
			playlistDAO.addVideoClipToPlaylist(playlist2, clip2);
			
			ArrayList<Playlist> allPlaylists = playlistDAO.getAllPlaylists();
			assertTrue(allPlaylists.contains(playlist) && allPlaylists.contains(playlist2));
			
			playlistDAO.deletePlaylist(playlist);
			playlistDAO.deletePlaylist(playlist2);
			videoDAO.deleteVideoClip(clip1);
			videoDAO.deleteVideoClip(clip2);
			
		} catch (Exception e) {
			fail("didn't work:" + e.getMessage());
		}
	}
}
