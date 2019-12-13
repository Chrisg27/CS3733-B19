package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import model.Playlist;
import model.VideoClip;

public class PlaylistsDAO {

	java.sql.Connection conn;
	
	/**
	 * Creates a new Playlist Data Access Object
	 */
    public PlaylistsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    /**
     * Gets a Playlist from the database
     * @param name the name of the playlist
     * @return a new Playlist object with the correct information
     * @throws Exception
     */
    public Playlist getPlaylist(String name) throws Exception {
        
        try {
            Playlist playlist = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE name=?;");
            ps.setString(1,  name);
            ResultSet resultSet = ps.executeQuery();
            
            //get all the clips associated with the playlist
            VideosDAO videoDAO = new VideosDAO();
            ArrayList<VideoClip> playlistClips = new ArrayList<VideoClip>();
            ArrayList<Integer> orderOfClips = new ArrayList<Integer>();
            
            boolean playlistExists = false;
            while (resultSet.next()) {
            	playlistExists = true;
            	if(!(resultSet.getString("clipURL") == null)) {
            		
            		orderOfClips.add(resultSet.getInt("clipOrder"));
            		Collections.sort(orderOfClips);
            		
            		int indexToAddToo = orderOfClips.indexOf(resultSet.getInt("clipOrder"));
            		
            		if(videoDAO.getVideoClip(resultSet.getString("clipURL")) != null) playlistClips.add(indexToAddToo, videoDAO.getVideoClip(resultSet.getString("clipURL")));
            		else playlistClips.add(indexToAddToo, new VideoClip(resultSet.getString("clipURL"), " ", " ", false));
            	}
            }
            
            if(playlistExists) playlist = generatePlaylist(name, playlistClips);
            resultSet.close();
            ps.close();
            
            return playlist;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting video clip: " + e.getMessage());
        }
    }
    
    /**
     * Deletes a Playlist from the database
     * @param playlist the Playlist to delete
     * @return true if it was able to delete a Playlist
     * @throws Exception
     */
    public boolean deletePlaylist(Playlist playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE name = ?;");
            ps.setString(1, playlist.getName());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected >= 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete playlist: " + e.getMessage());
        }
    }
    
    /**
     * Add an empty playlist to the database
     * @param playlist the new playlist to add
     * @return true if it is able to add the playlist
     * @throws Exception
     */
    public boolean addEmptyPlaylist(Playlist playlist) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE name = ?;");
            ps.setString(1, playlist.getName());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO Playlists (name, clipURL, clipOrder) values(?, ?, ?);");
            ps.setString(1, playlist.getName());
            ps.setString(2, null);
            ps.setString(3, null);
            ps.execute();
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add playlist: " + e.getMessage());
        }
    }
    
    /**
     * Adds a VideoClip to a Playlist
     * @param playlist the Playlist to add the video to
     * @param videoClip the VideoClip to add
     * @return true if it was able to add the video to the playlist
     * @throws Exception
     */
    public boolean addVideoClipToPlaylist(Playlist playlist, VideoClip videoClip) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE (name = ?) AND (clipURL = ?);");
            ps.setString(1, playlist.getName());
            ps.setString(2, videoClip.getClipURL());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }
            
            //check to see if an empty playlist exists
            Playlist newPlaylist = getPlaylist(playlist.getName());
            //remove it from the database if it has no VideoClips associated with it
            int numberOfClips = newPlaylist.getNumberOfClips();
            if(playlist != null & numberOfClips == 0) deletePlaylist(playlist);

            //add the new VideoClip and same Playlist back into the database
            ps = conn.prepareStatement("INSERT INTO Playlists (name, clipURL, clipOrder) values(?, ?, ?);");
            ps.setString(1, playlist.getName());
            ps.setString(2, videoClip.getClipURL());
            ps.setString(3, Integer.toString(numberOfClips + 1));
            ps.execute();
            
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add video into playlist: " + e.getMessage());
        }
    }
    
    /**
     * Deletes a VideoClip from a Playlist
     * @param playlist the Playlist to delete from
     * @param videoClip the VideoClip to delete
     * @return true if the VideoClip was able to be deleted
     * @throws Exception
     */
    public boolean deleteVideoClipFromPlaylist(Playlist playlist, VideoClip videoClip) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Playlists WHERE (name = ? AND clipURL = ?);");
            ps.setString(1, playlist.getName());
            ps.setString(2, videoClip.getClipURL());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            //if that was the only video in the playlist, add it back as empty
            if(!hasPlaylist(playlist)) addEmptyPlaylist(playlist);
            
            fixOrderNumbers(playlist);
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete video from playlist: " + e.getMessage());
        }
    }
    
    /**
     * Deletes a playlist from the database and then adds it back in. Used to update the order tag when a video from a playlist is removed
     * @param playlist playlist to reorder
     * @return true is reorder process was possible
     * @throws Exception
     */
    private boolean fixOrderNumbers(Playlist playlist) throws Exception {
    	try {
        	Playlist fullPlaylist = getPlaylist(playlist.getName());
        	if(fullPlaylist.getNumberOfClips() == 0) return true;
        	
        	deletePlaylist(playlist);
        	addEmptyPlaylist(playlist);
        	
        	Iterator<VideoClip> videoClipIterator = fullPlaylist.getVideoIterator();
        	
        	while(videoClipIterator.hasNext()) {
        		addVideoClipToPlaylist(fullPlaylist, videoClipIterator.next());
        	} return true;
        	
    	}catch (Exception e) {
    		throw new Exception("Failed to re oder video clips: " + e.getMessage());
    	}
    }
    
    /**
     * Returns true if the Playlist database contains a specific Playlist
     * @param playlist the Playlist to look for
     * @return true if the database contains that Playlist
     * @throws Exception
     */
    public boolean hasPlaylist(Playlist playlist) throws Exception{
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Playlists WHERE (name = ?);");
    		ps.setString(1, playlist.getName());
    		ResultSet resultSet = ps.executeQuery();
    		
    		if(resultSet.next()) return true;
    		else return false;
    		
    	} catch (Exception e) {
    		throw new Exception("Failed to check for playlist: " + e.getMessage());
    	}
    }
    
    /**
     * Gets all the playlists in the database
     * @return a list containing all the playlists in the database
     * @throws Exception
     */
    public ArrayList<Playlist> getAllPlaylists() throws Exception{
    	
        ArrayList<String> playlistNames = new ArrayList<String>();
        ArrayList<Playlist> allPlaylists = new ArrayList<Playlist>();
        
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM Playlists;";
            ResultSet resultSet = statement.executeQuery(query);

            //get the names of all the unique playlists
            while (resultSet.next()) {
                if(!playlistNames.contains(resultSet.getString("name"))) playlistNames.add(resultSet.getString("name"));
            }
            resultSet.close();
            statement.close();
            
            //get each playlist separately
            for(String playlistName : playlistNames) {
            	Playlist newPlaylist = getPlaylist(playlistName);
            	if(newPlaylist != null) allPlaylists.add(newPlaylist);
            	
            } return allPlaylists;

        } catch (Exception e) {
            throw new Exception("Failed in getting playlists: " + e.getMessage());
        }
    }
    
    /**
     * Generates a new Playlist
     * @param name the name of the playlist
     * @param playlistClips a list of VideoClips in the playlist
     * @return a new playlist
     * @throws Exception
     */
    private Playlist generatePlaylist(String name, ArrayList<VideoClip> playlistClips) throws Exception {  
        return new Playlist(name, playlistClips.toArray(new VideoClip[playlistClips.size()]));
    }
}
