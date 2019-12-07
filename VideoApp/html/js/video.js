//list of all local video URLs
var videoURLs = []

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
			html += "<td><input type=\"checkbox\" class=\"videoCheckbox\" id=cbv"+index+"></td>"
			html += "<td><video id=\"num"+index+"\" width-\"320\" height=\"240\" controls>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
			html += "<td>" + cur.speaker + "</td>"
			html += "<td>" + cur.associatedText + "</td>"
			html += "<td>" + cur.marked + "</td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("VideoTable")
	existingTable.innerHTML = html
}

function uploadVideo() {
	/* get speaker from input
	get associated text from input
	get file uploaded
	put all in a video object in database
	update display */
	
	var speakerInput = document.getElementById("videoSpeaker");
	var textInput = document.getElementById("videoText");
	var file = document.getElementById("videoFile")
	//getting the actual strings that the user input
	var speaker = speakerInput.value;
	var text = textInput.value;
	
	 var data = {};
	 
	 if((speaker !== "")&&(text !== "")&&(file !== NULL)) {
	 
		 data["speaker"]  = speaker;
		 data["dialogue"] = text;
	 
		 var reader = new FileReader();
		 reader.readAsDataURL(file);
	
		 data["base64EncodedVideo"] = reader.result;

		 var js = JSON.stringify(data);
		 console.log("JS:" + js);
		 var xhr = new XMLHttpRequest();
		 xhr.open("POST", uploadVideo, true);
	 
		 xhr.send(js);
	 } else {
		 window.alert("Please input all new video criteria.");
	 }

	 /* Response!! */
	 
}
function deleteVideo() {
	/* delete selected video from the database
	update display */
}
function markVideo() {
	/* mark selected video
	update display */
}
function unmarkVideo() {
	/* unmark selected video
	update display */
}