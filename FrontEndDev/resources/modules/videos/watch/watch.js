/**
 * Created by laurent on 16/04/15.
 */
angular.module('liztube.videos.watch',
    [
        "ngRoute",
        "liztube.moastr",
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

}).controller('watchCtrl', function ($sce,$rootScope, $scope, $routeParams, $route, moastr, videosService, constants,$location, $mdDialog) {
        $scope.errorUpdate = '';
        $scope.isEnableEditingVideo = false;

        $scope.showLink = function(ev) {
            var confirm = $mdDialog.confirm()
                .title('Copier ce lien pour le partager')
                .content($location.absUrl())
                .ariaLabel('Copy video link')
                .ok('OK')
                .targetEvent(ev);
            $mdDialog.show(confirm).then(function() {

            }, function() {

            });
        };

        function DialogController($scope, $mdDialog) {
            $scope.hide = function() {
                $mdDialog.hide();
            };
            $scope.cancel = function() {
                $mdDialog.cancel();
            };
            $scope.answer = function(answer) {
                $mdDialog.hide(answer);
            };
        }

        $scope.enableEditVideo = function(){
            $scope.isEnableEditingVideo = true;
        };

        var videoOwnerTest= function (userName) {
            if(window.user !== null){
                if(userName == window.user.pseudo )
                    return true;
                else
                    return false;
            }else{
                return false;
            }
        };

        var videoKey = $routeParams.videoKey;
        /**
         * Get video data
         */
        $scope.getVideoDesc = function(){
            videosService.getVideoData(videoKey).then(function(video){

                $scope.videoDesc = video;

                console.log(video);

                $scope.editVideo = videoOwnerTest(video.ownerPseudo);
                $scope.config = {
                    sources: [
                        {src: $sce.trustAsResourceUrl("/api/video/watch/"+videoKey), type: "video/mp4"}
                    ],
                    theme: "/app/dist/libs/videogular-themes-default/videogular.css",
                    plugins: {
                        poster: "/api/video/thumbnail/"+videoKey+"?width=1280&height=720",
                        controls: {
                            autoHide: true,
                            autoHideTime: 5000
                        }
                    }
                };

            },function(responses){
                if(responses.data.messages[0] === "#1101"){
                    moastr.error(constants.VIDEO_NOT_EXISTS, 'left right bottom');
                    $location.path('/');
                }else if(responses.data.messages[0] === "#1100"){
                    moastr.error(constants.VIDEO_NOT_EXISTS, 'left right bottom');
                    $location.path('/');
                }else{
                    moastr.error(constants.SERVER_ERROR, 'left right bottom');
                }
            });
        };


        /**
         * Update videoDesc
         */
        $scope.updateVideoDesc= function (){

            $rootScope.$broadcast('loadingStatus', true);

            videosService.updateVideoData($scope.videoDesc).then(function () {
                moastr.successMin(constants.UPDATE_VIDEO_DESCRIPTION_OK, 'top right');
                $scope.isEnableEditingVideo = false;
            }, function () {
                moastr.error(constants.SERVER_ERROR, 'left right bottom');
            }).finally(function () {
                $rootScope.$broadcast('loadingStatus', false);
            });


        };

});