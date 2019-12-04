package http;

import java.util.ArrayList;
import java.util.List;

import model.VideoClip;

public class SearchVideoResponse {

	public final List<VideoClip> list;
	public final int httpCode;
	public final String error;
	
	public SearchVideoResponse(List<VideoClip> list, int code) {
		this.list = list;
		this.httpCode= code;
		this.error = "";
	}
	
	public SearchVideoResponse(int code, String error) {
		list = new ArrayList<VideoClip>();
		this.httpCode = code;
		this.error = error;
	}
	
	public String toString() {
		if (list == null) { return "NoMatches"; }
		return "SearchVideos(" + list.size() + ")";
	}
	
}
