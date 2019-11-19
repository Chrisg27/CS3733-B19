package http;

import java.util.ArrayList;
import java.util.List;

import model.Playlist;

public class ListPlaylistsResponse {
	public final List<Playlist> list;
	public final int statusCode;
	public final String error;
	
	public ListPlaylistsResponse(List<Playlist> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public ListPlaylistsResponse(int code, String error) {
		list = new ArrayList<Playlist>();
		this.statusCode = code;
		this.error = error;
	}
	
	public String toString() {
		if (list == null) { return "EmptyPlaylists"; }
		return "ListPlaylists(" + list.size() + ")";
	}
}
