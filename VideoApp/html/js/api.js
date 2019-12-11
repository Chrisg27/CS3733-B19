// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var baseURL = "https://kjqrlwoold.execute-api.us-east-1.amazonaws.com/beta/" 

var getPlaylistsURL = baseURL + "playlists"   // GET
var addPlaylistURL = baseURL + "playlists" //POST
var addVideoToPlaylistURL = baseURL + "playlistVideos" //POST
var deletePlaylistURL = baseURL + "playlist" // POST 
var deleteVideoFromPlaylistURL = baseURL + "playlistVideo" //POST
var getPlaylistVideosURL = baseURL + "listPlaylistVideo" //GET
var registerSiteURL = baseURL + "registerSite" //POST
var getSiteURL = baseURL + "registerSite" //GET
var deleteSiteURL = baseURL + "unregisterSite" //POST
var getVideosURL = baseURL + "videos"     // GET
var uploadVideoURL = baseURL + "videos" //POST
var deleteVideoURL = baseURL + "video" //POST
var getPublicVideosURL = baseURL + "video" //GET