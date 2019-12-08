var currentPlaylistInView = ""

function processPlaylistResponse(result) {
  console.log("result:" + result)
  refreshPlaylistData()
}

/**
 * Refreshes the playlist elements on the page
 *    GET getPlaylistsURL
 *    RESPONSE list of playlists
 */
function refreshPlaylistData() {
	var xhr = sendRequest("GET", getPlaylistsURL, null);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			var currentPlaylists = JSON.parse(xhr.responseText).list;
			console.log(currentPlaylists);
			
			drawPlaylistTable(currentPlaylists);
			updatePlaylistSelects(currentPlaylists);
			
			if(currentPlaylistInView != ""){
				refreshVideosInPlaylist();
			}
			
			console.log("Success");
		} else {
			console.log("Failure");
		}
	};
}

/**
 * Updates the list of available playlists to add a video to
 * @param objArray
 */
function updatePlaylistSelects(objArray){
	var selectArrays = document.getElementsByName("PlaylistSelect");
	console.log(selectArrays);
	var html = "";
		
		objArray.forEach(function(cur, index){
			html += "<option id=pl"+index+">"
			html += cur.name
			html += "</option>"
		})
		selectArrays.forEach(function(curSelect){
			curSelect.innerHTML = html;
		})
}

/**
 * Refreshes the list of videos for the currently viewing playlist
 * 
 * POST getPlaylistVideosURL {[playlistName : name of the playlist]}
 * RESPONSE list of videos
 */
function refreshVideosInPlaylist(){
	var data = {}
	data["name"] = currentPlaylistInView;
	var xhr = sendRequest("POST", getPlaylistVideosURL, data);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
 	    if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				videos = JSON.parse(xhr.responseText).list
				drawVideosInPlaylistTable(videos)
 	    	} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
 	    	}
    	} else {
    		processPlaylistResponse("N/A");
    	}
	};
}

/**
 * Draws the playlist table
 * @param objArray table to draw
 */
function drawPlaylistTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Name</b></td></thead>"
		
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"PlaylistCheckbox\" id=\"PlaylistCheckbox" + index + "\"></td>"
			html += "<td>" + cur.name + "</td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("PlaylistTable");
	existingTable.innerHTML = html;
}

/**
 * Draws the table of videos in a playlist
 * @param objArray
 */
function drawVideosInPlaylistTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Video</b></td></thead>"
		
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"PlaylistVideoCheckbox\" id=\"PlaylistVideoCheckbox" + index + "\"></td>"
			html += "<td><video id=\"playlistVideo"+index+"\" width-\"320\" height=\"240\" controls>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("PlaylistVideoTable");

	console.log("html" + html);
	existingTable.innerHTML = html;
}

/**
 * Creates a new playlist
 * POST addPlaylistURL {[playlistName : name of the playlist]}
 */
function createPlaylist(){
	//getting the user's input
	var input = document.getElementById("NewPlaylistName");
	console.log(input);
	//getting the actual string that the user input
	var newPlaylistName = input.value;
	console.log(newPlaylistName);
	//if the string put in is not empty, add the name to the table
	
	if(newPlaylistName !== ""){
		
		var data = {}
		data["name"] = newPlaylistName;
		var xhr = sendRequest("POST", addPlaylistURL, data);
			
		//read the response
	    xhr.onloadend = function () {
	    	console.log(xhr);
	 	    console.log(xhr.request);
	 	    if (xhr.readyState == XMLHttpRequest.DONE) {
	 	    	if (xhr.status == 200) {
    				console.log ("XHR:" + xhr.responseText);
    				processPlaylistResponse(xhr.responseText);
	 	    	} else {
    				console.log("actual:" + xhr.responseText)
    				var js = JSON.parse(xhr.responseText);
    				var err = js["response"];
    				alert (err);
	 	    	}
 	    	} else {
 	    		processPlaylistResponse("N/A");
 	    	}
	    }
	}
	else {
		//user didn't put anything into the text box to name their playlist
		window.alert("Please enter a valid name.")
	}
}

/**
 * Deletes a playlist
 * POST deletePlaylist {[playlistVideo : name of playlist}}
 */
function deletePlaylist() {
	var index = getCheckBoxValue("PlaylistCheckbox");
	if(index === -1) return;
	
	var playlist = document.getElementById("PlaylistTable").rows[index + 1].cells[1].innerText;
	var data = {};
	data["name"] = playlist;
	var xhr = sendRequest("POST", deletePlaylistURL, data);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				processPlaylistResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processPlaylistResponse("N/A");
		}
	};
}

/**
 * Adds video to playlist
 * POST addVideoToPlaylistURL {[playlist : name of playlist], [videoUrl : url of video]} 
 */
function addVideoToPlaylist(){
	//get the playlist to add to
	var list = document.getElementById("LocalVideoPlaylistSelect")
	var playlist = list.options[list.selectedIndex].text
	console.log(playlist)
	
	var index = getCheckBoxValue("VideoTableCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("VideoTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
	console.log(videoURL);
	
	data = {}
	data["playlist"] = playlist;
	data["videoUrl"] = videoURL;
	var xhr = sendRequest("POST", addVideoToPlaylistURL, data);
	
	//read the response
    xhr.onloadend = function () {
    	console.log(xhr);
 	    console.log(xhr.request);
 	    if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	 if (xhr.status == 200) {
 	    		 console.log ("XHR:" + xhr.responseText);
 	    		 processPlaylistResponse(xhr.responseText);
 	    	 } else {
 	    		 console.log("actual:" + xhr.responseText)
 	    		 var js = JSON.parse(xhr.responseText);
 	    		 var err = js["response"];
 	    		 alert (err);
 	    	 }
 	    } else {
 	      processPlaylistResponse("N/A");
 	    }
    }
}

/**
 * Deletes a video
 * POST deleteVideoFromPlaylistURL {[playlist : name of playlist] [videoUrl : url of video]}
 */
function deleteVideoFromPlaylist() {
	var index = getCheckBoxValue("PlaylistVideoCheckbox");
	if(index === -1) return;
	var video = document.getElementById("PlaylistVideoTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
	var data = {};
	data["playlist"] = currentPlaylistInView;
	data["videoUrl"] = video;

	var xhr = sendRequest("POST", deleteVideoFromPlaylistURL, data);
	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				processPlaylistResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processPlaylistResponse("N/A");
		}
	};	
}

/**
 * Shows a list of videos for a playlist
 */
function openPlaylist() {
	var index = getCheckBoxValue("PlaylistCheckbox");

	console.log(index);
	if(index === -1) return;
	
	var playlist = document.getElementById("PlaylistTable").rows[index + 1].cells[1].innerText;
	currentPlaylistInView = playlist;

	console.log("playlist: " + currentPlaylistInView);
	document.getElementById("ViewedPlaylistName").innerHTML = playlist;
	refreshVideosInPlaylist();
}
/**
 * Plays each video segment in order
 */
function playPlaylist() {
	var videos = [];
	
	console.log(document.getElementById("PlaylistVideoTable").rows.length - 2);

	for(index = document.getElementById("PlaylistVideoTable").rows.length - 2; index >= 0; index--){
		videos[index] = document.getElementById("playlistVideo" + index);
		
		//if not the last video
		if(index !== document.getElementById("PlaylistVideoTable").rows.length - 2){
			videos[index].videoArray = videos;
			videos[index].currentIndex = index;
			videos[index].addEventListener("ended", function(event){event.currentTarget.videoArray[event.currentTarget.currentIndex + 1].play();});
		}
	}
	
	if(videos.length > 0){
		videos[0].play();
	}
}