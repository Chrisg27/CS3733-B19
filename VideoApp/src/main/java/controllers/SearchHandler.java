package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import dataBase.VideosDAO;
import http.SearchVideoRequest;
import http.SearchVideoResponse;
import model.VideoClip;

public class SearchHandler implements RequestHandler<SearchVideoRequest, SearchVideoResponse> {

	LambdaLogger logger;
	
	List<VideoClip> searchVideos(String speaker, String dialogue) throws Exception {
    	logger.log("in searchVideos"); 
    	VideosDAO dao = new VideosDAO();
    	ArrayList<VideoClip> clips = dao.getAllVideos();
    	ArrayList<VideoClip> results = new ArrayList<VideoClip>();
    	
    	for (int i = 0; i < clips.size(); i++) {
    		VideoClip clip = clips.get(i);
    		if (speaker.equals("") && dialogue.equals("")) {
    			if(clip.getSpeaker().equals(speaker) && clip.getAssociatedText().equals(dialogue))
    				results.add(clip);
    		}
    		else if (dialogue.equals("")) {
    			if(clip.getSpeaker().equals(speaker))
    				results.add(clip);
    		}
    		else if (speaker.equals("")) {
    			if(clip.getAssociatedText().equals(dialogue))
    				results.add(clip);
    		}
    		else {
    			results.add(clip);
    		}
    	}
    	
    	return results;
    }
	
	@Override
    public SearchVideoResponse handleRequest(SearchVideoRequest req, Context context) {
        logger = context.getLogger();
        logger.log("Loading Java handler to search videos");
        
        SearchVideoResponse response;
        
        try {
        	List<VideoClip> list = searchVideos(req.character, req.dialogue);
        	response = new SearchVideoResponse(list, 200);
        } catch (Exception e) {
        	response = new SearchVideoResponse(403, e.getMessage());
        }
        return response;
    }

}
