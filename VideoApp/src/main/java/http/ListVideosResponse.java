package http;

import java.util.ArrayList;
import java.util.List;

import model.VideoClip;

public class ListVideosResponse {
	public final List<VideoClip> list;
	public final int statusCode;
	public final String error;
	
	public ListVideosResponse(List<VideoClip> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListVideosResponse(int code, String error) {
		list = new ArrayList<VideoClip>();
		this.statusCode = code;
		this.error = error;
	}
	
	public String toString() {
		if (list == null) { return "EmptyVideos"; }
		return "ListVideos(" + list.size() + ")";
	}
}
