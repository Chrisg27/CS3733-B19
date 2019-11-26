/**
 * Refresh constant list from server
 *
 *    GET getVidoesURL
 *    RESPONSE  list of [URL, associatedText, speaker, isMarked]  
 */
function refreshConstantsList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", getVideosURL, true);
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
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
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

function drawVideoTable(objArray){
	console.log(objArray)
	var html = "<thead><td><b>Select</b></td><td><b>Name</b></td><td><b>Speaker</b></td><td><b>AssociatedText</b></td><td><b>Marked?</b></td></thead>"
objArray.forEach(function(cur, index){
	html += "<tr id="+index+">"
	html += "<td><input type=\"checkbox\" id="+index+"></td>"
	html += "<td>" + currentLists[index] + "</td>"
	html += "</tr>"
})

var existingTable = document.getElementById("PlaylistTable")
	existingTable.innerHTML = html
}