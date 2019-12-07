window.addEventListener('load', (event) => {
	console.log("LOAD INITIAL DATA");
	loadBaseData();
	console.log("LOADED");
    //log.textContent = log.textContent + 'load\n';
});

/**
 * Fills in initial table data from the database
 */
function loadBaseData(){
	refreshVideoTable();
	refreshPlaylistTable();
}

/**
 * Function for when things are not implemented yet
 */
function notDone() {
	window.alert("to be implemented later...");
}

/**
 * Sends a request to the server
 * @param requestType the type of HTTP request
 * @param requestURL the URL of the API
 * @param data the json data send with the HTTP request
 * @returns the XMLHTTPRequest object being used
 */
function sendRequest(requestType, requestURL, data){
	var xhr = new XMLHttpRequest();
	xhr.overrideMimeType("text/javascript");
	xhr.open(requestType, requestURL, true);
	
	if(requestType === "POST"){
		var js = JSON.stringify(data);
		console.log("JS:" + js);
		xhr.send(js);
	} else{
		xhr.send();
		
	} console.log("sent");
	
	return xhr;
}

/**
 * Returns the index of the checked checkbox for a certain checkbox class
 * @param checkBoxClass the checkBox class to search in
 * @returns the index in the table of the checked checkbox
 */
function getCheckBoxValue(checkBoxClass){
	var checkboxList = document.getElementsByClassName(checkBoxClass);
	console.log(checkboxList);
	var index = -1;
	if(checkboxList !== null) {
		for(var i = 0; i < checkboxList.length; i++) {
			if(checkboxList[i].checked === true){
				index = i;
				break;
			}
		}
		
	} return index;
}