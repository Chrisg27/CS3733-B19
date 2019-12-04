// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var baseURL = "https://qu71sq0yv8.execute-api.us-east-1.amazonaws.com/beta/" 

var getPlaylistsURL = baseURL + "playlists"   // GET
var getVideosURL = baseURL + "videos"     // GET
var addPlaylistURL = baseURL + "playlist" //POST
var addVideoToPlaylistURL = baseURL + "playlistVideos" //POST
var deletePlaylistURL = baseURL + "playlist" // POST 
