let currentPlaylists;
//all access driven through BASE. Must end with a SLASH
//be sure you change to accommodate your specific API Gateway entry point
var baseURL = "https://qu71sq0yv8.execute-api.us-east-1.amazonaws.com/beta/"; 

var getPlaylistsURL = baseURL + "playlists";   // GET
var getVideosURL = baseURL + "videos";     // GET


//{name:"Playlist 1", song: "songs"},{name: "Playlist 2", song: "songs"}

function createPlaylist(){
	//getting the user's input
	var input = document.getElementById("NewPlaylistName")
	console.log(input)
	//getting the actual string that the user input
	var newPlaylistName = input.value
	console.log(newPlaylistName)
	//if the string put in is not empty, add the name to the table
	
	if(newPlaylistName !== ""){
		/*
		write to db here
		*/
		window.alert("THIS WILL WORK AT SOME POINT :)")
	}
	else {
		//user didn't put anything into the text box to name their playlist
		window.alert("Please enter a valid name.")
	}
	
	/*
	read from db here
	
	refreshPlaylistTable()
	*/
}

function deletePlaylist() {
	var checkboxList = document.getElementsByTagName("checkbox");
	console.log(checkboxList)
	var index = 0
	if(checkboxList !== null) {
		for(var i = 0; i < checkboxList.length; i++) {
			if(checkboxList[i].checked === true){
				index = i
				break
			}
		}
	}
	document.getElementById("adminPlaylistTable").deleteRow(index)
	drawTable("adminPlaylistTable")
}

function drawTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Name</b></td></thead>"
	objArray.forEach(function(cur, index){
		html += "<tr id="+index+">"
		html += "<td><input type=\"checkbox\" id=cbp"+index+"></td>"
		html += "<td>" + cur.name + "</td>"
		html += "</tr>"
	})

	var existingTable = document.getElementById("PlaylistTable")
	existingTable.innerHTML = html
}


/*
 * 
 * 
 * Calls database and gets playlist info, calls drawTable
 * 
 * 
 */
function refreshPlaylistTable() {
	   var xhr = new XMLHttpRequest();
	   xhr.overrideMimeType("text/javascript")
	   xhr.open("GET", getPlaylistsURL, true);
	   xhr.send();
	   
	   console.log("sent");

	  // This will process results and update HTML as appropriate. 
	  xhr.onloadend = function () {
	    if (xhr.readyState == XMLHttpRequest.DONE) {
	      console.log ("XHR:" + xhr.responseText);
	      currentPlaylists = JSON.parse(xhr.responseText).list
	      console.log(currentPlaylists)
	      drawTable(currentPlaylists)
	      updatePlaylistSelects(currentPlaylists)
	      //processListResponse(xhr.responseText);
	      console.log("SUCCESS!!!!!!!!!!!!!!")
	    } else {
	      //processListResponse("N/A");
	    	console.log("FAILURE")
	      
	    }
	  };
}

/**
 * Refresh constant list from server
 *
 *    GET getVidoesURL
 *    RESPONSE  list of [URL, associatedText, speaker, isMarked]  
 */
function refreshVideoTable() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", getVideosURL, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      console.log(JSON.parse(xhr.responseText))
      drawVideoTable(JSON.parse(xhr.responseText).list)
      //processListResponse(xhr.responseText);
    } else {
      //processListResponse("N/A");
    	console.log("FAILURE")
    }
  };
}

function drawVideoTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Video</b></td><td><b>Speaker</b></td><td><b>Associated Text</b></td><td><b>Marked?</b></td></thead>"
	objArray.forEach(function(cur, index){
		html += "<tr id="+index+">"
		html += "<td><input type=\"checkbox\" id=cbv"+index+"></td>"
		html += "<td><video id=\"num0\" width-\"320\" height=\"240\" controls>"
		html += "<source src=" + cur.clipURL + " type=\"video/ogg\"> \"Your browser does not support this video tag\" </video></td>"
		html += "<td>" + cur.speaker + "</td>"
		html += "<td>" + cur.associatedText + "</td>"
		html += "<td>" + cur.marked + "</td>"
		html += "</tr>"
	})

	var existingTable = document.getElementById("VideoTable")
	existingTable.innerHTML = html
}

function loadBaseData(){
	refreshVideoTable();
	refreshPlaylistTable();
}

window.addEventListener('load', (event) => {
	console.log("LOAD INITIAL DATA")
	loadBaseData();
	console.log("LOADED")
    //log.textContent = log.textContent + 'load\n';
});

function notDone() {
	window.alert("to be implemented later...")
}

function updatePlaylistSelects(objArray){
	var selectArrays = document.getElementsByName("PlaylistSelect")
	console.log(selectArrays)
	var html = ""
		
		objArray.forEach(function(cur, index){
			html += "<option id=pl"+index+">"
			html += cur.name
			html += "</option>"
		})
		selectArrays.forEach(function(curSelect){
			curSelect.innerHTML = html
		})
}
