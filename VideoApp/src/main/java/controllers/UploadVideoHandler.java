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
	
	/** Store into RDS and S3 Bucket. Store speaker, dialogue, URL, and isMarked
	 * 
	 * @throws Exception 
	 */
	boolean uploadVideo(String clipURL, String associatedText, String speaker, byte[] videos) throws Exception {
		if (logger != null) { logger.log("in uploadVideo"); }
		VideosDAO dao = new VideosDAO();
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		ByteArrayInputStream bais = new ByteArrayInputStream(videos);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(videos.length);
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("princes3733", "videos/" + clipURL, bais, omd));
		
		// if we ever get here, then whole thing was stored
	
		// check if present
		VideoClip exist = dao.getVideoClip(clipURL);
		VideoClip video = new VideoClip (clipURL, associatedText, speaker, false);
		if (exist == null) {
			return dao.addVideoClip(video);
		} else {
			return false;
		}
	}
	
	/**Create S3 Bucket PHYSICAL CLIP
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
		
		PutObjectResult res = s3.putObject(new PutObjectRequest("princes3733", "videos/" + name, bais, omd));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
    @Override
    public UploadVideoResponse handleRequest(UploadVideoRequest req, Context context) {
    	logger = context.getLogger();
		logger.log(req.toString());
		
		byte[] encoded = java.util.Base64.getDecoder().decode(req.getBase64EncodedVideo());

		UploadVideoResponse response;
		try {
    		if (uploadVideo(req.name, req.dialogue, req.speaker, encoded)) {
    			response = new UploadVideoResponse(req.name, 200);
    		}
    		else {
    			response = new UploadVideoResponse(req.name, 422);
    		}
    	} catch (Exception e) {
    		response = new UploadVideoResponse("Unable to create playlist: " + req.name + "(" + e.getMessage() + ")", 400);
    	}
    	
    	return response;
    }

}
