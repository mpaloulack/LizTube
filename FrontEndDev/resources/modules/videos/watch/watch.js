/**
 * Created by laurent on 16/04/15.
 */
angular.module('liztube.video.watch',
    [
        "ngSanitize",
        "com.2fdevs.videogular",//Video reader
        "com.2fdevs.videogular.plugins.controls",//Controls
        "com.2fdevs.videogular.plugins.overlayplay",//Big play button
        "com.2fdevs.videogular.plugins.poster"//Thumbnail poster
    ]
).config(function ($routeProvider){
    $routeProvider.when("/test",{
        title: "LizTube - test",
        page: "test",
        controller: 'HomeCtrl',
        templateUrl: "watch.html"
    });
}).controller('HomeCtrl', function ($sce, $scope) {
        var videoKey = "3144c25c-2f0b-42b0-a954-e1a851a30c5d";
    $scope.config = {
        sources: [
            {src: $sce.trustAsResourceUrl("/api/video/watch/"+videoKey), type: "video/mp4"}
        ],
        theme: "/app/dist/libs/videogular-themes-default/videogular.css",
        plugins: {
            poster: "/api/video/thumbnail/"+videoKey
        }
    };
});