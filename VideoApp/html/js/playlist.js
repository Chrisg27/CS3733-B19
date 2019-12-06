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
	var xhr = sendRequest("GET", getPlaylistURL, null);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			var currentPlaylists = JSON.parse(xhr.responseText).list
			console.log(currentPlaylists)
			
			drawTable(currentPlaylists)
			updatePlaylistSelects(currentPlaylists)
			
			if(currentPlaylistInView != ""){
				refreshVideosInPlaylist();
			}
			
			console.log("Success")
		} else {
			console.log("Failure")
		}
	};
}

/**
 * Updates the list of available playlists to add a video too
 * @param objArray
 */
function updatePlaylistSelects(objArray){
	var selectArrays = document.getElementsByName("PlaylistSelect")
	console.log(selectArrays)
	var html = ""
		
		objArray.forEach(function(cur, index){
			html += "<option id=pl"+index+">"
			html += cur.name
			html += "</option>"
		})
		selectArrays.forEach(function(curSelect){
			curSelect.innerHTML = html
		})
}

/**
 * Refreshes the list of videos for the currently viewing playlist
 * 
 * POST getPlaylistVideosURL
 * RESPONSE list of videos
 */
function refreshVideosInPlaylist(){
	var data = {}
	data["name"] = currentPlaylistInView
	var js = JSON.stringify(data);
	
	var xhr = sendRequest("POST", getPlaylistVideosURL, js);

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
			html += "<td><input type=\"checkbox\" class=\"PlaylistCheckbox\" id="+index+"></td>"
			html += "<td>" + cur.name + "</td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("PlaylistTable")
	existingTable.innerHTML = html
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
			html += "<td><input type=\"checkbox\" class=\"VideoInPlaylistViewCheckbox\" id="+index+"></td>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("PlaylistViewTable")
	existingTable.innerHTML = html
}

/**
 * Creates a new playlist
 * POST addPlaylistURL {name : playlistName}
 */
function createPlaylist(){
	//getting the user's input
	var input = document.getElementById("NewPlaylistName")
	console.log(input)
	//getting the actual string that the user input
	var newPlaylistName = input.value
	console.log(newPlaylistName)
	//if the string put in is not empty, add the name to the table
	
	if(newPlaylistName !== ""){
		
		var data = {}
		data["name"] = newPlaylistName
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = sendRequest("POST", addPlaylistURL, js);
			
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
 * POST deletePlaylist {name : playlist}
 */
function deletePlaylist() {
	var index = getCheckBoxValue("PlaylistCheckBox");
	if(index === -1) return;
	
	var playlist = document.getElementById("PlaylistTable").rows[index].cells[0].innerHTML;
	var data = {};
	data["name"] = playlist;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	
	var xhr = sendRequest("POST", deletePlaylistURL, js);

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
 * POST addVideoToPlaylistURL {[playlist : name of playlist], [video : url of video]} 
 */
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
		
	var xhr = sendRequest("POST", addVideoToPlaylistURL, js);
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
 * POST deleteVideoFromPlaylistURL {[name : playlist] [video : url of video]}
 */
function deleteVideoFromPlaylist() {
	
	var video = document.getElementById("VideosInPlaylistTable").rows[index].cells[0].innerHTML;
	var data = {};
	data["name"] = currentPlaylistInView;
	data["clipURL"] = video;

	var js = JSON.stringify(data);
	console.log("JS:" + js);

	var xhr = sendRequest("POST", deleteVideoFromPlaylistURL, js);
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
	var index = getCheckBoxValue("PlaylistCheckBox");
	if(index === -1) return;
	
	var playlist = document.getElementById("PlaylistTable").rows[index].cells[0].innerHTML;
	currentPlaylistInView = playlist
	refreshVideosInPlaylist
}
/**
 * Plays each video segment in order
 * Copy the code where George does it
 */
function playPlaylist() {
	
}