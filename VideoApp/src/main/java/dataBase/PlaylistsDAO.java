package dataBase;

public class PlaylistsDAO {

	java.sql.Connection conn;
	
    public PlaylistsDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
}
