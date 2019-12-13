/**
 * Returns site url and api key
 * @param siteURL the url to look through
 * @returns {url : the url, key : the api key}
 */
function getURLandAPIKey(siteURL){
	
	var data = {}
	var indexOfURLEnd = siteURL.indexOf("?apikey=");
	
	if(indexOfURLEnd === -1){
		data["url"] = siteURL;
		data["key"] = null;
		
	} else{
		var newURL = siteURL.substring(0, indexOfURLEnd);
		var apiKey = siteURL.substring(indexOfURLEnd + 8, siteURL.length);
		data["url"] = newURL;
		data["key"] = apiKey;

		console.log(data);
		
	} return data
}

/**
 * Searches sites
 * @param sitesToSearch list of sites to search
 * @param index the index in the array to use
 */
function searchSites(sitesToSearch, index){
	var videos = [];
	var xhr;
	
	if(index === -1) return;
	
	if(index === 0) xhr = sendRequest("GET", sitesToSearch[0], null);
	
	else{
		var dataSeperation = getURLandAPIKey(sitesToSearch[index]);
		xhr = new XMLHttpRequest();
		xhr.open("GET", dataSeperation["url"], true);
		xhr.setRequestHeader("x-api-key", dataSeperation["key"]);
		xhr.send();
	}
	
	xhr.onloadend = function () {
		console.log(xhr);
	 	console.log(xhr.request);
 		if (xhr.readyState == XMLHttpRequest.DONE) {
 			
 			//Good response
 	    	 if (xhr.status == 200) {
 	    		 console.log ("XHR:" + xhr.responseText);
 	    		 console.log ("XHR:" + xhr.responseText);
 	    		 videos = (JSON.parse(xhr.responseText).segments !== undefined) ? JSON.parse(xhr.responseText).segments : JSON.parse(xhr.responseText).list;
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
 	    					 var videoText = (cur.associatedText !== undefined) ? cur.associatedText : cur.text;
 	    					 if(videoText.includes(textSearch)) textSearchArray.push(cur);
 	    				 })
 	    				 addToSearchTable(textSearchArray);
 	    			 }
 	    			 else if(textSearch === "") {
 	    				 var speakerSearchArray = [];
 	    				 console.log("VIDEOS " + videos)
 	    				 videos.forEach(function(cur, index) {
 	    					 console.log("CURRENT " + cur)
 	    					 console.log(cur);
 	    					 
 	    					var speakerText = (cur.speaker !== undefined) ? cur.speaker : cur.character;
 	    					if(speakerText.includes(speakerSearch)) speakerSearchArray.push(cur);
 	    				 })
 	    				 addToSearchTable(speakerSearchArray);
 	    				 
 	    			 } else {
						var bothSearchArray = [];
						videos.forEach(function(cur, index) {
							var videoText = (cur.associatedText !== undefined) ? cur.associatedText : cur.text;
							var speakerText = (cur.speaker !== undefined) ? cur.speaker : cur.character;
							
							if((videoText.includes(textSearch))&&(speakerText.includes(speakerSearch))) bothSearchArray.push(cur);
						})
						addToSearchTable(bothSearchArray);
					}
 	    		 }
 	    				 
 	    		 //Bad response
 	    	 } else {
 	    		 console.log("actual:" + xhr.responseText)
 	    		 var js = JSON.parse(xhr.responseText);
 	    		 var err = js["response"];
 	    		 alert (err);
 	    	 }
		}
 		
 		searchSites(sitesToSearch, index - 1);
	}
}

/**
 * Starts the search process by getting a list of the sites to search
 * @returns list of remote sites
 */
function startSearch(){
	
	clearSearchTable();
	var xhr = sendRequest("GET", getSiteURL, null);
	var sites = [];
	
	xhr.onloadend = function () {
		console.log(xhr);
	 	console.log(xhr.request);
 		if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	 if (xhr.status == 200) {
 	    		 console.log ("XHR:" + xhr.responseText);
 	    		 response = JSON.parse(xhr.responseText).list;

 	    		 response.forEach(function(cur, index){
 	    		 	sites.push(cur.siteURL);
 	    		 })
	 	    		 
 	    	 } else {
 	    		 console.log("actual:" + xhr.responseText)
 	    		 var js = JSON.parse(xhr.responseText);
 	    		 var err = js["response"];
 	    		 alert (err);
 	    	 }
		}
 		
		sites.unshift(getVideosURL);
		console.log(sites)
		searchSites(sites, sites.length - 1);
	}
}

/**
 * Creates a new table row for each video
 * @param objArray an array of videos
 */
function addToSearchTable(objArray){
	
	var searchTable = document.getElementById("SearchTable");
	var currentSize = searchTable.rows.length
	var url;
	var speaker;
	var text;
	
	objArray.forEach(function(cur, index){
		
		var tableRow = searchTable.insertRow(currentSize + index);

		url = (cur.clipURL !== undefined) ? cur.clipURL : cur.url;
		speaker = (cur.speaker !== undefined) ? cur.speaker : cur.character;
		text = (cur.associatedText !== undefined) ? cur.associatedText : cur.text;
		
		tableRow.id = index+currentSize;
		
		var col0 = tableRow.insertCell(0);
		col0.innerHTML = "<input type=\"radio\" name=\"SearchVideo\" class=\"VideoSearchCheckbox\" id=cbsv"+(index+currentSize)+">";
		
		var col1 = tableRow.insertCell(1);
		col1.innerHTML = "<video id=\"searchTable"+(index+currentSize)+"\" width-\"320\" height=\"240\" controls>" +
			"<source src=" + url + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video>";
		
		var col2 = tableRow.insertCell(2);
		col2.innerHTML = speaker;
		
		var col3 = tableRow.insertCell(3);
		col3.innerHTML = text
		
		/*html += "<td><input type=\"radio\" name=\"SearchVideo\" class=\"VideoSearchCheckbox\" id=cbsv"+(index+currentSize)+"></td>"
		html += "<td><video id=\"searchTable"+(index+currentSize)+"\" width-\"320\" height=\"240\" controls>"
		html += "<source src=" + url + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
		html += "<td>" + speaker + "</td>"
		html += "<td>" + text + "</td>"
		
		tableRow.innerHTML = html;*/

		console.log("DATA");
		console.log(tableRow);
		//searchTable.appendChild(tableRow);
	})
}

/**
 * Clears all of the videos out of the search table
 */
function clearSearchTable() {
	var html = "<thead><td><b>Select</b></td><td><b>Video</b></td><td><b>Speaker</b></td><td><b>Associated Text</b></td></thead>";
	var existingTable = document.getElementById("SearchTable")
	existingTable.innerHTML = html
}

/**
 * Adds selected video in the search table to a playlist
 */
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