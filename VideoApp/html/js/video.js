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
			html += "<td><input type=\"radio\" name=\"Video\" class=\"VideoTableCheckbox\" id=\"VideoTableCheckbox"+index+"\"></td>"
			html += "<td><video id=\"videoTable"+index+"\" width-\"320\" height=\"240\" controls>"
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
	var speakerInput = document.getElementById("videoSpeaker");
	var textInput = document.getElementById("videoText");
	var file = document.getElementById("videoFile")
	//getting the actual strings that the user input
	
	var videoName = file.value.split("\\").pop();
	var speaker = speakerInput.value;
	var text = textInput.value;
	var data = {};
	 
	if((videoName !== "")&&(speaker !== "")&&(text !== "")&&(file !== null)) {
	 
		data["clipUrl"] = videoName;
		data["speaker"]  = speaker;
		data["associatedText"] = text;
		var reader = new FileReader();
		reader.readAsDataURL(file.files[0]);
		reader.onload = function(e){
			data["base64EncodedVideo"] = reader.result.split(',')[1];
		console.log(data)
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
		}
		
		 
	} else {
		window.alert("Please input all new video criteria.");
	}	 
}

/**
 * deletes video
 * POST deleteVideoURL {clipUrl: url of the video}
 */
function deleteVideo() {
	var index = getCheckBoxValue("VideoTableCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("VideoTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
	var data = {};
	data["clipUrl"] = videoURL;
	var xhr = sendRequest("POST", deleteVideoURL, data);
	
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
	/* mark selected video (true)
	update display */
	var index = getCheckBoxValue("VideoTableCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("VideoTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
	var data = {};
	data["clipUrl"] = videoURL;
	data["mark"] = true;
	var xhr = sendRequest("POST", markVideoURL, data);
	
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
function unmarkVideo() {
	/* unmark selected video (false)
	update display */
	var index = getCheckBoxValue("VideoTableCheckbox");
	if(index === -1) return;
	
	var videoURL = document.getElementById("VideoTable").rows[index + 1].cells[1].getElementsByTagName("source")["0"]["src"];
	var data = {};
	data["clipUrl"] = videoURL;
	data["mark"] = false;
	var xhr = sendRequest("POST", markVideoURL, data);
	
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
