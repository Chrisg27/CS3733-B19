package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import dataBase.VideosDAO;
import http.DeleteVideoRequest;
import http.DeleteVideoResponse;
import http.UploadVideoRequest;
import http.UploadVideoResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteVideoHandlerTest extends LambdaTest{

    @Test
    public void testDeleteVideoHandler() {
    	
    	//First upload video
    	//get path of test video
    	String path = "src/test/resources/clip1.ogg";
    	File file = new File(path);
    	String absolutePath = file.getAbsolutePath();
    	System.out.println(absolutePath);
    	
    	//convert it to a byte array
    	byte[] bytesArray = new byte[(int) file.length()]; 
    	FileInputStream fis;
		try {
			fis = new FileInputStream(file);
	    	fis.read(bytesArray); //read file into bytes[]
	    	fis.close();
		} catch (Exception e) {
			System.out.println("Cannot convert file: " + e.getMessage());
		}
    	
    	//encode and upload
    	byte[] encoding = java.util.Base64.getEncoder().encode(bytesArray);
    	UploadVideoRequest testReq = new UploadVideoRequest("LambdaTestUploadVideo.ogg", "", "", new String(encoding));
    	UploadVideoResponse testRes = new UploadVideoHandler().handleRequest(testReq, createContext("create"));
    	
    	//Then delete Video
    	DeleteVideoRequest req = new DeleteVideoRequest("https://princess3733.s3.amazonaws.com/videos/LambdaTestUploadVideo.ogg");
    	DeleteVideoResponse res = new DeleteVideoHandler().handleRequest(req, createContext("delete"));
    	Assert.assertEquals("https://princess3733.s3.amazonaws.com/videos/LambdaTestUploadVideo.ogg", res.response);
    	Assert.assertTrue(res.httpCode == 200);
    }
    
    @Test
    public void testDeleteVideoError422(){
    	
    	//First upload video
    	//get path of test video
    	String path = "src/test/resources/clip1.ogg";
    	File file = new File(path);
    	String absolutePath = file.getAbsolutePath();
    	System.out.println(absolutePath);
    	
    	//convert it to a byte array
    	byte[] bytesArray = new byte[(int) file.length()]; 
    	FileInputStream fis;
		try {
			fis = new FileInputStream(file);
	    	fis.read(bytesArray); //read file into bytes[]
	    	fis.close();
		} catch (Exception e) {
			System.out.println("Cannot convert file: " + e.getMessage());
		}
    	
    	//encode and upload
    	byte[] encoding = java.util.Base64.getEncoder().encode(bytesArray);
    	UploadVideoRequest testReq = new UploadVideoRequest("LambdaTestUploadVideo.ogg", "", "", new String(encoding));
    	UploadVideoResponse testRes = new UploadVideoHandler().handleRequest(testReq, createContext("create"));
    	
    	//Then delete Video
    	DeleteVideoRequest req = new DeleteVideoRequest("failTest");
    	DeleteVideoResponse res = new DeleteVideoHandler().handleRequest(req, createContext("delete"));
    	Assert.assertTrue(res.httpCode == 422);
    	
    }
}
