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
    $routeProvider.when("/watch",{
        title: "LizTube - watch",
        page: "watch",
        controller: 'watchCtrl',
        templateUrl: "watch.html"
    });
}).controller('watchCtrl', function ($sce,$rootScope, $scope, $routeParams) {
        $rootScope.$on('$routeChangeSuccess', function () {
            console.log($routeParams.key);
        });

    /*var videoKey = $routeParams.key;
    $scope.config = {
        sources: [
            {src: $sce.trustAsResourceUrl("/api/video/watch/"+videoKey), type: "video/mp4"}
        ],
        theme: "/app/dist/libs/videogular-themes-default/videogular.css",
        plugins: {
            poster: "/api/video/thumbnail/"+videoKey+"?width=1280&height=720"
        }
    };*/
});