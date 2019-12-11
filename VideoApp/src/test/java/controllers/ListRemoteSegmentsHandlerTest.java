package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.RemoteSegmentsResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListRemoteSegmentsHandlerTest extends LambdaTest {

    @Test
    public void testListRemoteSegmentsHandler() {
        ListRemoteSegmentsHandler handler = new ListRemoteSegmentsHandler();
        RemoteSegmentsResponse response = handler.handleRequest(null, createContext("list"));
        Assert.assertEquals(200, response.statusCode);
    }
}
