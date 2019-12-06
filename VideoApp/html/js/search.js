function search() {
	/* if there is text in speaker field, use it as search criteria
	if there is text in associated text field, use it as search criteria
	if they both are filled, use both as search criteria
	get all local and remote videos and search in both for the given criteria */
	
	var xhr = new XMLHttpRequest();
	xhr.open("GET", getVideosURL, true);
	xhr.send();
	
	//read the response
    xhr.onloadend = function () {
    	console.log(xhr);
 	    console.log(xhr.request);
 	    if (xhr.readyState == XMLHttpRequest.DONE) {
 	    	if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				var videos = JSON.parse(xhr.responseText).list;
 	    	} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert (err);
 	    	}
	    }
    }
    
	//getting the user's input
	var speakerInput = document.getElementById("SpeakerSearch");
	var textInput = document.getElementById("TextSearch");
	//getting the actual string that the user input
	var speakerSearch = speakerInput.value;
	var textSearch = textInput.value;
	
	//make sure there is at least one search criteria
	
	if((speakerSearch !== "") && (textSearch !== "")) {
		if(speakerSearch === "") {
			var textSearchArray;
			videos.forEach(function(cur, index) {
				if(cur.associatedText.includes(textSearch)) textSearchArray.push(cur);
			})
			drawSearchTable(textSearchArray);
		}
		else if(textSearch === "") {
			var speakerSearchArray;
			videos.forEach(function(cur, index) {
				if(cur.speaker.includes(speakerSearch)) speakerSearchArray.push(cur);
			})
			drawSearchTable(speakerSearchArray);
		}
		else {
			var bothSearchArray;
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
	
}

function drawSearchTable(objArray) {
	var html = "<thead><td><b>Select</b></td><td><b>Name</b></td></thead>"
		
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"videoSearchCheckbox\" id=cbsv"+index+"></td>"
			html += "<td><video id=\"num"+index+"\" width-\"320\" height=\"240\" controls>"
			html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
		})

	var existingTable = document.getElementById("SearchTable")
	existingTable.innerHTML = html
}