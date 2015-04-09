angular.module("liztube.upload.video",[
    "liztube.moastr",
    "ngRoute",
    "liztube.upload.video.page",
    'angularFileUpload' //https://github.com/danialfarid/angular-file-upload
]).controller("uploadVideoCtrl", function($scope, $http, $upload, constants, moastr) {

    $scope.uploadRate = 0;
    $scope.fileName = "";
    $scope.videoLoading = false;

    $scope.$on('loadingUploadVideoForHeader', function(event, video) {
        $scope.videoLoading = true;
        $scope.fileName = "Téléchargement de la vidéo : " + video.title;
        $mdSidenav('right').toggle();

        $upload.upload({
            url: '/api/video/upload',
            fields: {
                title: video.title,
                description: video.description,
                isPublic: video.isPublic,
                isPublicLink: video.isPublicLink
            },
            file: video.file
        }).progress(function (evt) {
            $scope.uploadRate = parseInt(100.0 * evt.loaded / evt.total);
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total) + "%";
        }).success(function (data, status, headers, config) {
            console.log('file "' + config.file.name + '" uploaded. Response: ' +
            JSON.stringify(data));
        }).error(function (data, status, headers, config){
            console.log("data " +data.messages);
            moastr.error(constants.SERVER_ERROR);
        });
    });

    $scope.closeProgressBar = function(){
        $scope.videoLoading = false;
        $scope.$emit('removeNotification', true);
    };

}).directive('uploadVideo', function () {
    return {
        restrict: 'E',
        controller: 'uploadVideoCtrl',
        templateUrl: "uploadVideo.html",
        scope: {
            type: "@"
        }
    };
});