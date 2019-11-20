package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
     * 
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM constants WHERE name = ?;");
            ps.setString(1, video.getClipURL());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                VideoClip v = generateVideoClip(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO constants (clipURL,associatedText,episode,speaker,duration,isMarked) values(?,?,?,?,?,?);");
            ps.setString(1,  video.getClipURL());
            ps.setString(2,  video.getAssociatedText());
            ps.setString(3, video.getEpisode());
            ps.setString(4, video.getSpeaker());
            ps.setDouble(5, video.getDuration());
            ps.setBoolean(6, video.isMarked());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert videoClipt: " + e.getMessage());
        }
    }
    
    private VideoClip generateVideoClip(ResultSet resultSet) throws Exception {
        String clipURL  = resultSet.getString("clipURL");
        String associatedText = resultSet.getString("associatedText");
        String episode = resultSet.getString("episode");
        String speaker = resultSet.getString("Speaker");
        double duration = resultSet.getDouble("Duration");
        boolean isMarked = resultSet.getBoolean("isMarked");
        
        return new VideoClip (clipURL, associatedText, episode, speaker, duration, isMarked);
    }

}
