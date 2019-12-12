import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Assert;

import http.PlaylistRequest;
import http.PlaylistVideoRequest;
import http.DeleteVideoRequest;
import http.RegisterSiteRequest;
import http.UploadVideoRequest;

import http.ListRemoteSitesResponse;
import http.ListVideosResponse;
import http.ListPlaylistVideosResponse;
import http.ListPlaylistsResponse;
import http.MarkVideoResponse;
import http.PlaylistVideoResponse;
import http.RegisterSiteResponse;
import http.RemoteSegmentsResponse;
import http.DeleteVideoResponse;
import http.UploadVideoResponse;

public class DefinitionsTest {

	@Test
	public void testRequests() {
		PlaylistRequest req1 = new PlaylistRequest("name");
		req1.setName("name2");
		Assert.assertEquals(req1.getName(), "name2");
		
		PlaylistVideoRequest req2 = new PlaylistVideoRequest("name", "url");
		req2.setPlaylist("name2");
		req2.setVideoUrl("url2");
		Assert.assertEquals(req2.getPlaylist(), "name2");
		Assert.assertEquals(req2.getVideoUrl(), "url2");
		
		DeleteVideoRequest req3 = new DeleteVideoRequest("url");
		req3.setClipUrl("url2");
		Assert.assertEquals(req3.getClipUrl(), "url2");
		
		RegisterSiteRequest req4 = new RegisterSiteRequest("url");
		req4.setUrl("url2");
		Assert.assertEquals(req4.getUrl(), "url2");
		
		UploadVideoRequest req5 = new UploadVideoRequest("url", "speaker", "text", "encoded");
		req5.setClipUrl("url2");
		req5.setSpeaker("speaker2");
		req5.setAssociatedText("text2");
		req5.setBase64EncodedVideo("encoded2");
		Assert.assertEquals(req5.getClipUrl(), "url2");
		Assert.assertEquals(req5.getSpeaker(), "speaker2");
		Assert.assertEquals(req5.getAssociatedText(), "text2");
		Assert.assertEquals(req5.getBase64EncodedVideo(), "encoded2");
	}
	
	@Test
	public void testResponses() {
		ListRemoteSitesResponse r1 = new ListRemoteSitesResponse(400, "error");
		System.out.println(r1.toString());
		
		ListVideosResponse r2 = new ListVideosResponse(400, "error");
		System.out.println(r2.toString());
		
		ListPlaylistVideosResponse r3 = new ListPlaylistVideosResponse(400, "error");
		System.out.println(r3.toString());
		
		ListPlaylistsResponse r4 = new ListPlaylistsResponse(400, "error");
		System.out.println(r4.toString());
		
		RemoteSegmentsResponse r5 = new RemoteSegmentsResponse(400, "error");
		System.out.println(r5.toString());
		
		MarkVideoResponse r6 = new MarkVideoResponse("s", 400, "error");
		System.out.println(r6.toString());
		
		PlaylistVideoResponse r7 = new PlaylistVideoResponse("s", 400, "error");
		System.out.println(r7.toString());
		
		DeleteVideoResponse r8 = new DeleteVideoResponse("s", 400, "error");
		System.out.println(r8.toString());
		
		UploadVideoResponse r9 = new UploadVideoResponse("s", 400, "error");
		System.out.println(r9.toString());
	}

}
