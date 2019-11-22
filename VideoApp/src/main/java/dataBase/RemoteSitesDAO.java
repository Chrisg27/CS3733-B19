package dataBase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import model.RemoteSite;
import model.VideoClip;

public class RemoteSitesDAO {

	java.sql.Connection conn;
	
	/**
	 * Creates a new RemoteSite Data Access Object
	 */
    public RemoteSitesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    
    /**
     * Adds a RemoteSite to the database
     * @param remoteSite the RemoteSite to have
     * @return true if the RemoteSite was able to be added
     * @throws Exception
     */
    public boolean addRemoteSite(RemoteSite remoteSite) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM RemoteSites WHERE siteURL = ?;");
            ps.setString(1, remoteSite.getSiteURL());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO RemoteSites (siteURL) values(?);");
            ps.setString(1, remoteSite.getSiteURL());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert remoteSite: " + e.getMessage());
        }
    }
    
    /**
     * Deletes a RemoteSite from the database
     * @param remoteSite RemoteSite to delete from the database
     * @return true if the remoteSite was able to be deleted
     * @throws Exception
     */
    public boolean deleteRemoteSite(RemoteSite remoteSite) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM RemoteSites WHERE siteURL = ?;");
            ps.setString(1, remoteSite.getSiteURL());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete remoteSite: " + e.getMessage());
        }
    }
    
    /**
     * Returns a list of all the remote sites
     * @return a list of all the remote sites
     * @throws Exception
     */
    public ArrayList<RemoteSite> getAllRemoteSites() throws Exception {
        
        ArrayList<RemoteSite> allRemoteSites = new ArrayList<RemoteSite>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM RemoteSites";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                RemoteSite rs = generateRemoteSite(resultSet);
                allRemoteSites.add(rs);
            }
            resultSet.close();
            statement.close();
            return allRemoteSites;

        } catch (Exception e) {
            throw new Exception("Failed in getting remote sites: " + e.getMessage());
        }
    }
    
    /**
     * Generates a new RemoteSite
     * @param resultSet the information from the database
     * @return a new RemoteSite object
     * @throws Exception
     */
    private RemoteSite generateRemoteSite(ResultSet resultSet) throws Exception {
        String siteURL = resultSet.getString("siteURL");
        
        return new RemoteSite(siteURL);
    }
}