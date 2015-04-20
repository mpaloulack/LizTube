/**
 User data service : to get/set data from the API

 **/

angular.module('liztube.dataService.videosService', [
    'restangular'
]).factory('videosService', function (Restangular) {
    function baseVideo(){
        return Restangular.one("video");
    }

    return {
        getVideos : getVideos,
        getVideoThumbnail : getVideoThumbnail,
        getVideoWatch :getVideoWatch,
        getVideoData : getVideoData
    };

    /**
     Get videos
     **/
    function getVideos(orderBy, params) {
        return baseVideo().one("search").getList(orderBy, params);
    }

    /**
     * Get thumbnails videos
     */
    function getVideoThumbnail(key ,params) {
        return baseVideo().one("thumbnail").customGET(key, params);
    }

    /**
     * Get video watch
     */
    function getVideoWatch(key){
        return baseVideo().one("watch").customGET(key);
    }

    /**
     * get video data
     */
    function getVideoData(key){
        return baseVideo().customGET(key);
    }
});