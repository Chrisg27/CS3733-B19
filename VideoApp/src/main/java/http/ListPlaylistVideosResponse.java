package http;

import java.util.ArrayList;
import java.util.List;

import model.VideoClip;

public class ListPlaylistVideosResponse {
	public final List<VideoClip> list;
	public final int statusCode;
	public final String error;
	
	public ListPlaylistVideosResponse(List<VideoClip> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListPlaylistVideosResponse(int code, String error) {
		list = new ArrayList<VideoClip>();
		this.statusCode = code;
		this.error = error;
	}
	
	public String toString() {
		if (list == null) { return "EmptyPlaylistVideos"; }
		return "ListPlaylistVideos(" + list.size() + ")";
	}
}
