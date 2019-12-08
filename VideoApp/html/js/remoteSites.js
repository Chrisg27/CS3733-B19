function processSiteResponse(result) {
	  console.log("result:" + result);
	  refreshSiteData();
}

/**
 * Refreshes remote site data
 * GET getSiteURL
 * RESPONSE list of remote sites
 */
function refreshSiteData() {	
	var xhr = sendRequest("GET", getSiteURL, null);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function () {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log ("XHR:" + xhr.responseText);
			var currentSites = JSON.parse(xhr.responseText).list;
			console.log(currentSites);
			drawRemoteSiteTable(currentSites);
			
			console.log("Success");
		} else {
			console.log("Failure");
		}
	};
}

/**
 * Draws remote site table
 * @param objArray remote site information
 */
function drawRemoteSiteTable(objArray) {
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>URL</b></td></thead>"
		
		objArray.forEach(function(cur, index){
			html += "<tr id="+index+">"
			html += "<td><input type=\"checkbox\" class=\"SiteCheckbox\" id=\"SiteCheckbox" + index + "\"></td>"
			html += "<td>" + cur.siteURL + "</td>"
			html += "</tr>"
		})

	var existingTable = document.getElementById("SiteTable");
	existingTable.innerHTML = html;
}

/**
 * Registers a remote site
 * POST registerSiteURL {[url: url of the remote site]}
 */
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
		data["url"] = newSiteURL;
		var xhr = sendRequest("POST", registerSiteURL, data);
			
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
 * Deletes a remote site
 * POST deleteSiteURL {[url : url of the remote site]}
 */
function deleteSite() {
	//get the name of the currently selected checkbox
	console.log(getCheckBoxValue("SiteCheckbox"));
	index = getCheckBoxValue("SiteCheckbox");
	if(index === -1) return;
	
	var remoteSite = document.getElementById("SiteTable").rows[index + 1].cells[1].innerText;
	console.log(remoteSite);
	var data = {};
	data["url"] = remoteSite;

	var xhr = sendRequest("POST", deleteSiteURL, data);

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