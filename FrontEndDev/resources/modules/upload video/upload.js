angular.module('liztube.upload.video', [
    "liztube.moastr",
    'ngRoute'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/upload",{
        title: "LizTube - vidéo upload",
        page: "Upload",
        controller: 'FileUploadController',
        templateUrl: "upload.html"
    });
})
.controller('FileUploadController', function($scope, moastr, constants) {
    $scope.submit= function() {
        var files = $scope.video.files;
        if (files && files.length) {
            video = {
                file: $scope.video.files[0],
                title: $scope.video.title,
                description: $scope.video.description,
                confidentiality: $scope.video.confidentiality
            };
            $scope.$emit('loadingUploadVideo', video);
            $scope.$emit('notification', true);
        }else{
            moastr.error(constants.NO_FILE_SELECTED);
        }
    };
    $scope.$watch('video.files', function() {
        if($scope.video){
            $scope.isValidFile($scope.video);
        }
    });
    $scope.isValidFile = function(video){
        if(video.files && video.files.length){
            if(video.files[0].type != "video/mp4") {
                moastr.error(constants.FILE_TYPE);
            }else{
                if ($scope.video.files[0].size > 521142272) {
                    moastr.error(constants.FILE_SIZE);
                }
            }
        }
    };
});