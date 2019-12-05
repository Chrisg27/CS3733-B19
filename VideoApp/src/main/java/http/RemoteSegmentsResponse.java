package http;

import java.util.ArrayList;
import java.util.List;

import model.VideoClip;

public class RemoteSegmentsResponse {
	public final List<VideoClip> segments;
	public final int statusCode;
	public final String error;
	
	public RemoteSegmentsResponse (List<VideoClip> list, int code) {
		this.segments = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public RemoteSegmentsResponse (int code, String errorMessage) {
		this.segments = new ArrayList<VideoClip>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (segments == null) { return "EmptySegments"; }
		return "Segments(" + segments.size() + ")";
	}
}
