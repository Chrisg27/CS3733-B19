package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.VideosDAO;
import http.DeleteVideoRequest;
import http.DeleteVideoResponse;
import model.VideoClip;

public class DeleteVideoHandler implements RequestHandler<DeleteVideoRequest, DeleteVideoResponse> {

	public LambdaLogger logger = null;
	
	@Override
	public DeleteVideoResponse handleRequest(DeleteVideoRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete video");

		DeleteVideoResponse response = null;
		logger.log(req.toString());

		VideosDAO dao = new VideosDAO();

		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		VideoClip video = new VideoClip(req.url, "", "", false);
		try {
			if (dao.deleteVideoClip(video)) {
				response = new DeleteVideoResponse(req.name, 200);
			} else {
				response = new DeleteVideoResponse(req.name, 422, "Unable to delete video.");
			}
		} catch (Exception e) {
			response = new DeleteVideoResponse(req.name, 403, "Unable to delete video: " + req.name + "(" + e.getMessage() + ")");
		}

		return response;
	}

}
