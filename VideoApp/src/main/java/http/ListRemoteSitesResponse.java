package http;

import java.util.ArrayList;
import java.util.List;

import model.RemoteSite;

public class ListRemoteSitesResponse {
	public final List<RemoteSite> list;
	public final int statusCode;
	public final String error;
	
	public ListRemoteSitesResponse(List<RemoteSite> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListRemoteSitesResponse(int code, String error) {
		list = new ArrayList<RemoteSite>();
		this.statusCode = code;
		this.error = error;
	}
	
	public String toString() {
		if (list == null) { return "EmptyRemoteSites"; }
		return "ListRemoteSites(" + list.size() + ")";
	}
}
