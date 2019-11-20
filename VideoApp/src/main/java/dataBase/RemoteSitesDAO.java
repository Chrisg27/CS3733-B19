package dataBase;

public class RemoteSitesDAO {

	java.sql.Connection conn;
	
    public RemoteSitesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
}