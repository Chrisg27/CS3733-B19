function processVideoResponse(result) {
  console.log("result:" + result)
  refreshVideoTable()
}

/**
 * Refresh local video list table
 *
 *    GET getVidoesURL
 *    RESPONSE  list of [URL, associatedText, speaker, isMarked]  
 */
function refreshVideoTable() {
	var xhr = sendRequest("GET", getVideosURL, null);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			console.log(JSON.parse(xhr.responseText))
			drawVideoTable(JSON.parse(xhr.responseText).list)
		} else {
			console.log("FAILURE")
		}
	};
}

/**
 * Draws the video table
 * @param objArray list of videos
 */
function drawVideoTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Video</b></td><td><b>Speaker</b></td><td><b>Associated Text</b></td><td><b>Marked?</b></td></thead>"
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"VideoTableCheckbox\" id=\"VideoTableCheckbox"+index+"\"></td>"
			html += "<td><video original-videourl=\"" + cur.clipURL +"\" id=\"videoTable"+index+"\" width-\"320\" height=\"240\" controls>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
			html += "<td>" + cur.speaker + "</td>"
			html += "<td>" + cur.associatedText + "</td>"
			html += "<td>" + cur.marked + "</td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("VideoTable");
	existingTable.innerHTML = html;
}

/**
 * Uploads video
 * POST uoloadVideoURL {[clipUrl : video name] [speaker : speaker in video] [associatedText : text in video] [base64EncodedVideo]} 
 */
function uploadVideo() {
	var videoNameInput = document.getElementById("videoName");
	var speakerInput = document.getElementById("videoSpeaker");
	var textInput = document.getElementById("videoText");
	var file = document.getElementById("videoFile")
	//getting the actual strings that the user input
	var videoName = videoNameInput.value;
	var speaker = speakerInput.value;
	var text = textInput.value;
	var data = {};
	 
	if((videoName !== "")&&(speaker !== "")&&(text !== "")&&(file !== NULL)) {
	 
		data["clipUrl"] = videoName;
		data["speaker"]  = speaker;
		data["associatedText"] = text;
		var reader = new FileReader();
		reader.readAsDataURL(file);
		data["base64EncodedVideo"] = reader.result;
		var xhr = sendRequest("POST", uploadVideoURL, data);
		 
		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function () {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log ("XHR:" + xhr.responseText);
					processVideoResponse(xhr.responseText);
				} else {
					console.log("actual:" + xhr.responseText)
					var js = JSON.parse(xhr.responseText);
					var err = js["error"];
					alert (err);
				}
			} else {
				processVideoResponse("N/A");
			}
		};
		 
	} else {
		window.alert("Please input all new video criteria.");
	}	 
}

/**
 * deletes video
 * POST deleteVideoURL {clipUrl: url of the video}
 */
function deleteVideo() {
	var index = getCheckboxValue("VideoTableCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("VideoTable").rows[index + 1].cells[1].getAttribute("original-videourl");
	var data = {};
	data["clipUrl"] = videoURL;
	var xhr = sendRequest("POTS", deleteVideoURL, data);
	
	//Read response 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				processVideoResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processVideoResponse("N/A");
		}
	};
}

/**
 * 
 * @returns
 */
function markVideo() {
	/* mark selected video
	update display */
}

/**
 * 
 * @returns
 */
function unmarkVideo() {
	/* unmark selected video
	update display */
}