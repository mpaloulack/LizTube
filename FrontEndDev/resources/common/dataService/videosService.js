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
        getVideoData : getVideoData
    };

    /**
     Get videos
     **/
    function getVideos(orderBy, params) {
        return baseVideo().one("search").getList(orderBy, params);
    }

    /**
     Get video data
     **/
    function getVideoData(key){
        return baseVideo().customGET(key);
    }
});