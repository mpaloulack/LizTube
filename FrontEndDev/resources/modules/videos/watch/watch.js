/**
 * Created by laurent on 16/04/15.
 */
angular.module('liztube.videos.watch',
    [
        "ngRoute",
        "liztube.dataService.videosService",
        "ngSanitize",
        "com.2fdevs.videogular",//Video reader
        "com.2fdevs.videogular.plugins.controls",//Controls
        "com.2fdevs.videogular.plugins.overlayplay",//Big play button
        "com.2fdevs.videogular.plugins.poster"//Thumbnail poster
    ]
).config(function ($routeProvider){
        $routeProvider.when("/watch/:videoKey",{
            title: "LizTube - vidéo",
            page: "vidéo",
            controller: 'watchCtrl',
            templateUrl: "watch.html",
            accessAnonymous : true
        });

}).controller('watchCtrl', function ($sce,$rootScope, $scope, $routeParams, $route, videosService) {
        $scope.errorUpdate = '';

        var videoKey = $routeParams.videoKey;
        /**
         * Get video data
         */
        $scope.getVideoDesc = function(){
            videosService.getVideoData(videoKey).then(function(video){

                $scope.videoDesc = video;

                $scope.config = {
                    sources: [
                        {src: $sce.trustAsResourceUrl("/api/video/watch/"+videoKey), type: "video/mp4"}
                    ],
                    theme: "/app/dist/libs/videogular-themes-default/videogular.css",
                    plugins: {
                        poster: "/api/video/thumbnail/"+videoKey+"?width=1280&height=720"
                    }
                };

            },function(responses){
                console.log(responses);
                moastr.error(constants.SERVER_ERROR, 'left right bottom');
            });
        };


});