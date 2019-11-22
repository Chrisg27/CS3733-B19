package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.VideoClip;

public class VideosDAO {
	
	java.sql.Connection conn;
	
	/**
	 * Creates a new Videos Data Access Object
	 */
    public VideosDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    /**
     * Gets a VideoClip from the database
     * @param clipURL the url of the clip being accessed
     * @return a new VideoClip object with the correct information
     * @throws Exception
     */
    public VideoClip getVideoClip(String clipURL) throws Exception {
        
        try {
            VideoClip video = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Videos WHERE clipURL=?;");
            ps.setString(1,  clipURL);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                video = generateVideoClip(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return video;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting video clip: " + e.getMessage());
        }
    }
    
    /**
     * Deletes a VideoClip from the database
     * @param video the video to delete
     * @return true if the VideoClip was able to be deleted
     * @throws Exception
     */
    public boolean deleteVideoClip(VideoClip video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Videos WHERE clipURL = ?;");
            ps.setString(1, video.getClipURL());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete clip: " + e.getMessage());
        }
    }

    /**
     * Adds a VideoClip to the database
     * @param video the VideoClip to add
     * @return true if it was able to add the VideoClip
     * @throws Exception
     */
    public boolean addVideoClip(VideoClip video) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Videos WHERE clipURL = ?;");
            ps.setString(1, video.getClipURL());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO Videos (clipURL,associatedText,speaker,isMarked) values(?,?,?,?);");
            ps.setString(1,  video.getClipURL());
            ps.setString(2,  video.getAssociatedText());
            ps.setString(3, video.getSpeaker());
            ps.setBoolean(4, video.isMarked());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert videoClip: " + e.getMessage());
        }
    }
    
    /**
     * Returns a list of all the videos in the database
     * @return a list of all videos
     * @throws Exception
     */
    public ArrayList<VideoClip> getAllVideos() throws Exception {
        
        ArrayList<VideoClip> allVideos= new ArrayList<VideoClip>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM Videos";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                VideoClip v = generateVideoClip(resultSet);
                allVideos.add(v);
            }
            resultSet.close();
            statement.close();
            return allVideos;

        } catch (Exception e) {
            throw new Exception("Failed in getting videos: " + e.getMessage());
        }
    }
    
    /**
     * Generates a new VideoClip object
     * @param resultSet the information from the database
     * @return a new VideoClip object
     * @throws Exception
     */
    private VideoClip generateVideoClip(ResultSet resultSet) throws Exception {
        String clipURL  = resultSet.getString("clipURL");
        String associatedText = resultSet.getString("associatedText");
        String speaker = resultSet.getString("Speaker");
        boolean isMarked = resultSet.getBoolean("isMarked");
        
        return new VideoClip (clipURL, associatedText, speaker, isMarked);
    }

}
