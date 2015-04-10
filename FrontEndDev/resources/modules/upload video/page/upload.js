angular.module('liztube.upload.video.page', [
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
    var isPublic = false;
    var isPublicLink = false;
    $scope.submit= function() {
        var files = $scope.video.files;
        if (files && files.length) {
            if($scope.video.confidentiality == '0'){
                isPublic = false;
                isPublicLink = false;
            }else if($scope.video.confidentiality == '1'){
                isPublic = true;
                isPublicLink = true;
            }else{
                isPublic = false;
                isPublicLink = true;
            }
            video = {
                file: $scope.video.files[0],
                title: $scope.video.title,
                description: $scope.video.description,
                isPublic: isPublic,
                isPublicLink: isPublicLink
            };
            $scope.$emit('loadingUploadVideo', video);
            $scope.$emit('addNotification', true);
        }else{
            moastr.error(constants.NO_FILE_SELECTED, 'left right bottom');
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
                moastr.error(constants.FILE_TYPE_ERROR, 'left right bottom');
            }else{
                if ($scope.video.files[0].size > constants.FILE_SIZE_ALLOWED) {
                    moastr.error(constants.FILE_SIZE_ERROR, 'left right bottom');
                }
            }
        }
    };
});