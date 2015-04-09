/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "liztube.moastr",
    "ngRoute",
    "liztube.upload.video",
    'angularFileUpload' //https://github.com/danialfarid/angular-file-upload
]).controller("headerCtrl", function($scope, $mdSidenav, $http, $upload, constants, moastr) {

    $scope.isLoading = false;
    $scope.videoLoading = false;
    $scope.showNotification = false;
    $scope.uploadRate = 0;
    $scope.fileName = "";
    $scope.notification = 0;
    $scope.$on('loadingUploadVideoForHeader', function(event, video) {
        $scope.videoLoading = true;
        $scope.fileName = "Téléchargement de la vidéo : " + video.title;
        $mdSidenav('right').toggle();

        $upload.upload({
            url: '/api/video/upload',
            fields: {
                title: video.title,
                description: video.description,
                isPublic: video.confidentiality,
                isPublicLink: video.confidentiality
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

    $scope.$on('notificationForHeader', function(event, bool) {
        $scope.showNotification = bool;
        $scope.notification = $scope.notification + 1;
    });

    $scope.$on('loadingStatusForHeader', function(event, bool) {
        $scope.isLoading= bool;
        $scope.uploadRate = 0;
    });

    $scope.closeProgressBar = function(){
        $scope.videoLoading = false;
        $scope.showNotification = false;
    };

    $scope.toggleRight = function() {
        $mdSidenav('right').toggle();
    };

}).directive('header', function () {
    return {
        restrict: 'E',
        controller: 'headerCtrl',
        templateUrl: "header.html"
    };
});
