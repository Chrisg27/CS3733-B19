package controllers;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.RemoteSitesDAO;
import http.RegisterSiteRequest;
import http.RegisterSiteResponse;
import model.RemoteSite;

public class UnregisterSiteHandler implements RequestHandler<RegisterSiteRequest, RegisterSiteResponse> {

	LambdaLogger logger;
	
	boolean unregisterSite(String url) throws Exception {
		if (logger != null) { logger.log("in unregisterSite"); }
		RemoteSitesDAO dao = new RemoteSitesDAO();
		
		ArrayList<RemoteSite> sites = dao.getAllRemoteSites();
		boolean exist = false;
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).getSiteURL().contentEquals(url)) exist = true;
		}
		if (exist) {
			return dao.deleteRemoteSite(new RemoteSite(url));
		}
		else {
			return false;
		}
	}
	
    @Override
    public RegisterSiteResponse handleRequest(RegisterSiteRequest req, Context context) {
    	logger = context.getLogger();
    	logger.log("Loading Java handler to unregister remote sites");
    	
    	RegisterSiteResponse response;
    	logger.log(req.toString());
    	
    	try {
    		if (unregisterSite(req.url)) {
    			response = new RegisterSiteResponse(req.url, 200);
    		}
    		else {
    			response = new RegisterSiteResponse(req.url, 422);
    		}
    	} catch (Exception e) {
    		response = new RegisterSiteResponse("Unable to unregister remote site: " + req.url + "(" + e.getMessage() + ")", 400);
    	}
    	return response;
    }

}
