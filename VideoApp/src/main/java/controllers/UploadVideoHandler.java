package controllers;

import java.io.ByteArrayInputStream;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import dataBase.VideosDAO;
import http.PlaylistResponse;
import http.UploadVideoRequest;
import http.UploadVideoResponse;
import model.VideoClip;

public class UploadVideoHandler implements RequestHandler<UploadVideoRequest, UploadVideoResponse> {

	LambdaLogger logger;
	
	private AmazonS3 s3 = null;
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean uploadVideo(String name, String associatedText, String speaker, boolean isMarked) throws Exception {
		if (logger != null) { logger.log("in uploadVideo"); }
		VideosDAO dao = new VideosDAO();
		
		String baseURL = "https://princess3733.s3.amazonaws.com/videos/";
		
		VideoClip video = new VideoClip (baseURL + name, associatedText, speaker, isMarked);
		return dao.addVideoClip(video);
	}
	
	/**Stores in S3 Bucket
	 * 
	 * @throws exception
	 */
	boolean uploadSystemVideo(String name, byte[] videos) throws Exception{
		if (logger != null) { logger.log("in uploadSystemVideo"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		ByteArrayInputStream bais = new ByteArrayInputStream(videos);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(videos.length);
		
		s3.putObject(new PutObjectRequest("princess3733", "videos/" + name, bais, omd));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
    @Override
    public UploadVideoResponse handleRequest(UploadVideoRequest req, Context context) {
    	logger = context.getLogger();
		logger.log(req.toString());

		UploadVideoResponse response;
		try {
			byte[] encoded = java.util.Base64.getDecoder().decode(req.getBase64EncodedVideo());
			
				if(uploadSystemVideo(req.getName(), encoded) && uploadVideo(req.getName(), req.getSpeaker(), req.getAssociatedText(), false)) {
					response = new UploadVideoResponse(req.getName(), 200);
					
				} else {
					response = new UploadVideoResponse(req.getName(), 422);
				}
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
			response = new UploadVideoResponse("Unable to upload video: " + req.getName() + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
    	
    	/*logger = context.getLogger();
		logger.log(req.toString());
		
		byte[] encoded = java.util.Base64.getDecoder().decode(req.getBase64EncodedVideo());

		UploadVideoResponse response;
		try {
    		if (uploadVideo(req.name, req.dialogue, req.speaker, false)) {
    			response = new UploadVideoResponse(req.name, 200);
    		}
    		else {
    			response = new UploadVideoResponse(req.name, 422);
    		}
    	} catch (Exception e) {
    		response = new UploadVideoResponse("Unable to create playlist: " + req.name + "(" + e.getMessage() + ")", 400);
    	}
    	
    	return response;
    }*/

}
