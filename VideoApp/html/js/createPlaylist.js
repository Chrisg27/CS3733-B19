function processCreatePlaylistResponse(result) {
  // Can grab any DIV or SPAN HTML element and can then manipulate its
  // contents dynamically via javascript
  console.log("result:" + result)

  refreshPlaylistTable()
}

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
	 		      processCreatePlaylistResponse(xhr.responseText);
	 	    	 } else {
	 	    		 console.log("actual:" + xhr.responseText)
	 				  var js = JSON.parse(xhr.responseText);
	 				  var err = js["response"];
	 				  alert (err);
	 	    	 }
	 	    } else {
	 	      processCreatePlaylistResponse("N/A");
	 	    }
	 	  }
	}
	else {
		//user didn't put anything into the text box to name their playlist
		window.alert("Please enter a valid name.")
	}
}