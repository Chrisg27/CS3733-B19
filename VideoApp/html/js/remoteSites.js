let currentSites;

function addSite(){
	//getting the user's input
	var input = document.getElementById("siteURL");
	console.log(input);
	//getting the actual string that the user input
	var newSiteURL = input.value;
	console.log(newSiteURL);
	//if the string put in is not empty, add the name to the table
	if(newSiteURL !== ""){
		
		var data = {};
		data["URL"] = newSiteURL;
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", registerSiteURL, true);

		// send the collected data as JSON
		xhr.send(js);
			
		//read the response
	    xhr.onloadend = function () {
	    	console.log(xhr);
	 	    console.log(xhr.request);
	 	    if (xhr.readyState == XMLHttpRequest.DONE) {
	 	    	if (xhr.status == 200) {
    				processSiteResponse(xhr.responseText);
	 	    	} else {
    				console.log("actual:" + xhr.responseText)
    				var js = JSON.parse(xhr.responseText);
    				var err = js["response"];
    				alert (err);
	 	    	}
 	    	} else {
 	    		processSiteResponse("N/A");
 	    	}
	    }
	}
	else {
		//user didn't input anything
		window.alert("Please enter a non-empty URL.")
	}
}

/**
 * Deletes a playlist
 * POST deletePlaylist {name : playlist}
 */
function deleteSite() {
	//get the name of the currently selected checkbox
	getCheckBoxValue(SiteCheckbox);
	if(index === -1) return;
	
	var remoteSite = document.getElementById("SiteTable").rows[index].cells[0].innerHTML;
	var data = {};
	data["name"] = remoteSite;

	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", deleteSiteURL, true);  // Can't be DELETE since then no data sent via JSON

	// send the collected data as JSON
	xhr.send(js);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log ("XHR:" + xhr.responseText);
				processSiteResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["error"];
				alert (err);
			}
		} else {
			processSiteResponse("N/A");
		}
	};
}

function drawRemoteSiteTable(objArray) {
	var html = "<thead><td><b>Select</b></td><td><b>URL</b></td></thead>"
		
	objArray.forEach(function(cur, index){
		html += "<tr id="+index+">"
		html += "<td><input type=\"checkbox\" class=\"SiteCheckbox\" id="+index+"></td>"
		html += "<td>" + cur.siteURL + "</td>"
		html += "</tr>"
	})

	var existingTable = document.getElementById("SiteTable")
	existingTable.innerHTML = html;
}

function processSiteResponse(result) {
	  console.log("result:" + result)
	  refreshSiteData()
}

function refreshSiteData() {
	var xhr = new XMLHttpRequest();
	xhr.overrideMimeType("text/javascript")
	xhr.open("GET", getSiteURL, true);
	xhr.send();
	   
	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			currentSites = JSON.parse(xhr.responseText).list
			console.log(currentSites)
			
			drawRemoteSiteTable(currentSites)
			
			console.log("Success")
		} else {
			console.log("Failure")
		}
	};
}