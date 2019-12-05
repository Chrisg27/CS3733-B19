var videosURLsInCurrentPlaylist = []
var currentPlaylistInView = ""

function processPlaylistResponse(result) {
  console.log("result:" + result)
  refreshPlaylist()
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
 * Refreshes the playlist elements
 *
 *    GET getPlaylistsURL
 *    RESPONSE  list of playlists
 */
function refreshPlaylist() {
	var xhr = new XMLHttpRequest();
	xhr.overrideMimeType("text/javascript")
	xhr.open("GET", getPlaylistsURL, true);
	xhr.send();
	   
	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			currentPlaylists = JSON.parse(xhr.responseText).list
			console.log(currentPlaylists)
			
			drawTable(currentPlaylists)
			updatePlaylistSelects(currentPlaylists)
			
			if(currentPlaylistInView != ""){
				refreshVideosInPlaylist();
			}
			
			console.log("SUCCESS!!!!!!!!!!!!!!")
		} else {
			console.log("FAILURE")
		}
	};
}

/**
 * Refreshes the list of videos for the currently viewing playlist
 * 
 * GET getPlaylistVideosURL
 * RESPONSE list of videos
 */
function refreshVideosInPlaylist(){
	var xhr = new XMLHttpRequest();
	xhr.overrideMimeType("text/javascript")
	xhr.open("GET", getPlaylistVideosURL, true);
	xhr.send();
	   
	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			videos = JSON.parse(xhr.responseText).list
			console.log(videos)
			
			console.log("SUCCESS!!!!!!!!!!!!!!")
		} else {
			console.log("FAILURE")
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
			html += "<td><input type=\"checkbox\" class=\"playlistCheckbox\" id="+index+"></td>"
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
		
		data = {}
		data["name"] = newPlaylistName
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", addPlaylistURL, true);

		// send the collected data as JSON
		xhr.send(js);
			
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
	
	//get the name of the currently selected checkbox
	var checkboxList = document.getElementsByClassName("playlistCheckbox");
	console.log(checkboxList)
	var index = 0
	if(checkboxList !== null) {
		for(var i = 0; i < checkboxList.length; i++) {
			if(checkboxList[i].checked === true){
				index = i
				break
			}
		}
	}
	
	var playlist = document.getElementById("PlaylistTable").rows[index].cells[0].innerHTML;
	var data = {};
	data["name"] = playlist;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", deletePlaylistURL, true);  // Can't be DELETE since then no data sent via JSON

	// send the collected data as JSON
	xhr.send(js);

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
	
	//get the name of the currently selected checkbox
	var checkboxList = document.getElementsByClassName("VideoInPlaylistViewCheckbox");
	console.log(checkboxList)
	var index = 0
	if(checkboxList !== null) {
		for(var i = 0; i < checkboxList.length; i++) {
			if(checkboxList[i].checked === true){
				index = i
				break
			}
		}
	}
	
	var video = document.getElementById("VideosInPlaylistTable").rows[index].cells[0].innerHTML;
	var data = {};
	data["playlist"] = currentPlaylistInView;
	data["video"] = video;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", deleteVideoFromPlaylistURL, true);  // Can't be DELETE since then no data sent via JSON

	// send the collected data as JSON
	xhr.send(js);

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
function openPlaylist() {
	/* open a new window of selected playlist
	display all video segments (to be played in order)*/
}
function playPlaylist() {
	/* play video segments in order */
}