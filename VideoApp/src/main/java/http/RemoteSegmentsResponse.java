package http;

import java.util.ArrayList;
import java.util.List;

import model.Segment;

public class RemoteSegmentsResponse {
	public final List<Segment> segments;
	public final int statusCode;
	public final String error;
	
	public RemoteSegmentsResponse (List<Segment> list, int code) {
		this.segments = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public RemoteSegmentsResponse (int code, String error) {
		this.statusCode = code;
		this.error = error;
		segments = new ArrayList<Segment>();
	}

	public String toString() {
		if (segments == null) { return "EmptySegments"; }
		return "Segments(" + segments.size() + ")";
	}
}
