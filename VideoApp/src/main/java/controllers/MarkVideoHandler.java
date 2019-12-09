package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.VideosDAO;
import http.MarkVideoRequest;
import http.MarkVideoResponse;
import http.PlaylistResponse;
import model.VideoClip;

public class MarkVideoHandler implements RequestHandler<MarkVideoRequest, MarkVideoResponse> {

	LambdaLogger logger;
	
	boolean markVideo(String url, boolean mark) throws Exception {
		if (logger != null) { logger.log("in markVideo"); }
		VideosDAO dao = new VideosDAO();
		
		VideoClip exist = dao.getVideoClip(url); //check if video clip exists
		if (exist != null) { //if it does, get all its attributes
			String speaker = exist.getSpeaker();
			String associatedText = exist.getAssociatedText();
			boolean isMarked = exist.isMarked();
			if (isMarked == mark) { //if video is already marked/unmarked as desired, return true
				return true;
			} else { //otherwise remove from database and add again with mark changed
				dao.deleteVideoClip(exist);
				return dao.addVideoClip(new VideoClip(url, speaker, associatedText, mark));
			}
		} else { //if it doesn't exist, return false
			return false;
		}
	}
	
    @Override
    public MarkVideoResponse handleRequest(MarkVideoRequest req, Context context) {
        logger = context.getLogger();
        logger.log("Loading Java handler to mark videos");
        
        MarkVideoResponse response;
        logger.log(req.toString());
        
        try {
        	if (markVideo(req.clipUrl, req.mark)) {
        		response = new MarkVideoResponse(req.clipUrl + " " + req.mark, 200);
        	} else {
        		response = new MarkVideoResponse(req.clipUrl + " " + req.mark, 422);
        		
        	}
        } catch (Exception e) {
        	response = new MarkVideoResponse("Unable to mark video: " + req.clipUrl + "(" + e.getMessage() + ")", 400);
        }
        return response;
    }

}
