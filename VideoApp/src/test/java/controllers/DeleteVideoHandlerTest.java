package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.DeleteVideoRequest;
import http.DeleteVideoResponse;
import http.UploadVideoRequest;
import http.UploadVideoResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteVideoHandlerTest extends LambdaTest{

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("DeleteVideoHandler");

        return ctx;
    }

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
    	UploadVideoResponse testRes = new UploadVideoHandler().handleRequest(testReq, createContext("UploadVideoHandler"));
    	
    	//Then delete Video
    	DeleteVideoRequest req = new DeleteVideoRequest("https://princess3733.s3.amazonaws.com/videos/LambdaTestUploadVideo.ogg", "", "", false);
    	DeleteVideoResponse res = new DeleteVideoHandler().handleRequest(req, createContext("DeleteVideoHandler"));
    	Assert.assertEquals("https://princess3733.s3.amazonaws.com/videos/LambdaTestUploadVideo.ogg", res.response);
    }
}
