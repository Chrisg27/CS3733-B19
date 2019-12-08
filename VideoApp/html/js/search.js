function search() {
	/* if there is text in speaker field, use it as search criteria
	if there is text in associated text field, use it as search criteria
	if they both are filled, use both as search criteria
	get all local and remote videos and search in both for the given criteria */
	let videos;
	var xhr = sendRequest("GET", getVideosURL, null);
	
	//read the response
    xhr.onloadend = function () {
    	console.log(xhr);
 	    console.log(xhr.request);
 	    if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				videos = JSON.parse(xhr.responseText).list;
				//getting the user's input
				var speakerInput = document.getElementById("SpeakerSearch");
				var textInput = document.getElementById("TextSearch");
				//getting the actual string that the user input
				var speakerSearch = speakerInput.value;
				var textSearch = textInput.value;
				
				//DEBUG CHECKING ASSIGNMENT
				console.log({"SI": speakerInput, "TI": textInput, "SS": speakerSearch, "TS": textSearch});
				
				//make sure there is at least one search criteria
				console.log(videos);
				if((speakerSearch !== "") || (textSearch !== "")) {
					if(speakerSearch === "") {
						var textSearchArray = [];
						videos.forEach(function(cur, index) {
							if(cur.associatedText.includes(textSearch)) textSearchArray.push(cur);
						})
						drawSearchTable(textSearchArray);
					}
					else if(textSearch === "") {
						var speakerSearchArray = [];
						console.log("VIDEOS " + videos)
						videos.forEach(function(cur, index) {
							console.log("CURRENT " + cur)
							console.log(cur);
							if(cur.speaker.includes(speakerSearch)) speakerSearchArray.push(cur);
						})
						drawSearchTable(speakerSearchArray);
					}
					else {
						var bothSearchArray = [];
						videos.forEach(function(cur, index) {
							if((cur.associatedText.includes(textSearch))&&(cur.speaker.includes(speakerSearch))) bothSearchArray.push(cur);
						})
						drawSearchTable(bothSearchArray);
					}
				}
				else {
					//user didn't put any search criteria in either text box
					window.alert("Please enter search criteria.")
				}
 	    	} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
 	    	}
	    }
    }
}

function drawSearchTable(objArray) {
	var html = "<thead><td><b>Select</b></td><td><b>Name</b></td><td><b>Speaker</b></td><td><b>Associated Text</b></td></thead>"
		
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"VideoSearchCheckbox\" id=cbsv"+index+"></td>"
			html += "<td><video id=\"searchTable"+index+"\" width-\"320\" height=\"240\" controls>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
			html += "<td>" + cur.speaker + "</td>"
			html += "<td>" + cur.associatedText + "</td>"
		})

	var existingTable = document.getElementById("SearchTable")
	existingTable.innerHTML = html
}

function addSearchToPlaylist(){
	//get the playlist to add to
	var list = document.getElementById("SearchVideoPlaylistSelect")
	var playlist = list.options[list.selectedIndex].text
	console.log(playlist)
	
	var index = getCheckBoxValue("VideoSearchCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("SearchTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
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