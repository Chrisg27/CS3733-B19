function processAddVideoToPlaylistResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result)

  refreshPlaylistVideosTable() //Not written yet
}

function addVideoToPlaylist(){
	//get the playlist to add to
	var list = document.getElementById("PlaylistSelect")
	var playlist = list.options[list.selectedIndex].text
	console.log(playlist)
	
	//get the video to add, how to do this?
	var videoURL
	console.log(videoURL)
	
	data = {}
	data["playlist"] = playlist
	data["video"] = videoURL
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", addVideoToPlaylistURL, true);

	// send the collected data as JSON
	xhr.send(js);
		
	//read the response
    xhr.onloadend = function () {
    	console.log(xhr);
 	    console.log(xhr.request);
 	    if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	 if (xhr.status == 200) {
 		      console.log ("XHR:" + xhr.responseText);
 		      processCreatePlaylistResponse(xhr.responseText);
 	    	 } else {
 	    		 console.log("actual:" + xhr.responseText)
 				  var js = JSON.parse(xhr.responseText);
 				  var err = js["response"];
 				  alert (err);
 	    	 }
 	    } else {
 	      processAddVideoToPlaylistResponse("N/A");
 	    }
 	  }
}