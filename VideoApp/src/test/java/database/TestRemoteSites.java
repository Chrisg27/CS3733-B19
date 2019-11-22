package database;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import dataBase.RemoteSitesDAO;
import model.RemoteSite;

public class TestRemoteSites {

	@Test
	public void testCreateDelete() {
		
		try {
	    	RemoteSitesDAO remoteSiteDAO = new RemoteSitesDAO();
	    	RemoteSite remoteSite = new RemoteSite("1");
	    	
	    	assertTrue(remoteSiteDAO.addRemoteSite(remoteSite));
	    	assertTrue(remoteSiteDAO.deleteRemoteSite(remoteSite));
    	
		} catch (Exception e) {
			fail ("didn't work:" + e.getMessage());
		}
	}
	
	@Test
	public void testGetAllRemoteSites() {
		
		try {
	    	RemoteSitesDAO remoteSiteDAO = new RemoteSitesDAO();
	    	RemoteSite remoteSite = new RemoteSite("1");
	    	RemoteSite remoteSite2 = new RemoteSite("2");
	    	
	    	remoteSiteDAO.addRemoteSite(remoteSite);
	    	remoteSiteDAO.addRemoteSite(remoteSite2);
	    	ArrayList<RemoteSite> allRemoteSites = new ArrayList<RemoteSite>();
	    	
	    	allRemoteSites = remoteSiteDAO.getAllRemoteSites();
	    	assertTrue(allRemoteSites.contains(remoteSite) && allRemoteSites.contains(remoteSite2));
	    	
	    	remoteSiteDAO.deleteRemoteSite(remoteSite);
	    	remoteSiteDAO.deleteRemoteSite(remoteSite2);
    	
		} catch (Exception e) {
			fail ("didn't work:" + e.getMessage());
		}
	}

}
