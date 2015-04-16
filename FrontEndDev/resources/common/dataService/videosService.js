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
        getVideos : getVideos
    };

    /**
     Get videos
     **/
    function getVideos(orderBy, params) {
        return baseVideo().one("search").getList(orderBy, params);
    }
});