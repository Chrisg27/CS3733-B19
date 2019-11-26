/**
 * Refresh constant list from server
 *
 *    GET getPlaylistsURL
 *    RESPONSE  list of [name, list of videos]
 */
function refreshPlaylistTable() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", getPlaylistsURL, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Rebuild table based on the contents of the library.
 */
function processListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var constList = document.getElementById('constantList');
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var constantJson = js.list[i];
    console.log(constantJson);
    
    var cname = constantJson["name"];
    var cval = constantJson["value"];
    var sysvar = constantJson["system"];
    
    if (sysvar) {
    	output = output + "<div id=\"const" + cname + "\"><b>" + cname + ":</b> = " + cval + "<br></div>";
    } else {
    	output = output + "<div id=\"const" + cname + "\"><b>" + cname + ":</b> = " + cval + "(<a href='javaScript:requestDelete(\"" + cname + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
    }
  }

  // Update computation result
  constList.innerHTML = output;
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
		currentLists.push(newPlaylistName)
		
		/* write to database here */
		
		input.value = ""
		drawPlaylistTable(currentLists)
	}
	else {
		//user didn't put anything into the text box to name their playlist
		window.alert("Please enter a valid name.")
	}
	
	/*
	write to db here
	*/
	}
  
  function drawPlaylistTable(objArray){
		console.log(objArray)
		var html = "<thead><td><b>Select</b></td><td><b>Name</b></td></thead>"
	objArray.forEach(function(cur, index){
		html += "<tr id="+index+">"
		html += "<td><input type=\"checkbox\" id="+index+"></td>"
		html += "<td>" + currentLists[index] + "</td>"
		html += "</tr>"
	})

	var existingTable = document.getElementById("PlaylistTable")
		existingTable.innerHTML = html
	}
  
  
