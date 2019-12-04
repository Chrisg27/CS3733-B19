package controllers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.RemoteSitesDAO;
import http.ListRemoteSitesResponse;
import model.RemoteSite;

public class ListRemoteSitesHandler implements RequestHandler<Object, ListRemoteSitesResponse> {

public LambdaLogger logger;
	
	/**
	 * Loads remote sites from RDS
	 * @return list of remote sites from RDS
	 * @throws Exception
	 */
	List<RemoteSite> getRemoteSites() throws Exception {
		logger.log("in getRemoteSites");
		RemoteSitesDAO dao = new RemoteSitesDAO();
		
		return dao.getAllRemoteSites();
	}
	
	public ListRemoteSitesResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java handler to list all remote sites");
		
		ListRemoteSitesResponse response;
		try {
			List<RemoteSite> list = getRemoteSites();
			response = new ListRemoteSitesResponse(list, 200);
		} catch (Exception e) {
			response = new ListRemoteSitesResponse(403, e.getMessage());
		}
		
		return response;
	}
}
