package controllers;

import java.io.ByteArrayInputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.clouddirectory.model.DeleteObjectResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import dataBase.VideosDAO;
import http.DeleteVideoRequest;
import http.DeleteVideoResponse;
import model.VideoClip;

public class DeleteVideoHandler implements RequestHandler<DeleteVideoRequest, DeleteVideoResponse> {

	public LambdaLogger logger = null;
	
	private AmazonS3 s3 = null;
	
	/** Deletes from RDS.
	 * 
	 * @throws Exception 
	 */
	boolean deleteVideo(String clipURL) throws Exception {
		if (logger != null) { logger.log("in deleteVideo"); }
		VideosDAO dao = new VideosDAO();
		
		VideoClip exist = dao.getVideoClip(clipURL);
		VideoClip video = new VideoClip (clipURL, "", "", false);
		if (exist != null) {
			return dao.deleteVideoClip(video);
		} else {
			return false;
		}
	}
	
	/**Deletes from S3 Bucket
	 * 
	 * @throws exception
	 */
	boolean deleteSystemVideo(String clipURL) throws Exception{
		if (logger != null) { logger.log("in uploadSystemVideo"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		
		String videoBaseURL = "https://princess3733.s3.amazonaws.com/videos/";
			
		Regions clientRegion = Regions.US_EAST_1;
        String bucketName = "princess3733";
        String keyName = "videos/" +  clipURL.substring(clipURL.indexOf(videoBaseURL) + videoBaseURL.length());
        
        System.out.println(keyName);

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new ProfileCredentialsProvider()).withRegion(clientRegion).build();

            s3Client.deleteObject(new DeleteObjectRequest(bucketName, keyName));
        } catch (Exception e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
            
        } return true;
	}
	
	@Override
	public DeleteVideoResponse handleRequest(DeleteVideoRequest req, Context context) {
		
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete video");

		DeleteVideoResponse response = null;
		logger.log(req.toString());
		
		try {
			if(deleteVideo(req.getClipUrl()) && deleteSystemVideo(req.getClipUrl())) {
				response = new DeleteVideoResponse(req.getClipUrl(), 200);
				
			} else {
				response = new DeleteVideoResponse("Unable to delete video " + req.getClipUrl(), 422);
			}
		} catch (Exception e) {
			response = new DeleteVideoResponse("Unable to delete video: " + req.getClipUrl() + "(" + e.getMessage() + ")", 403);
		}
		
		return response;
	}
}

