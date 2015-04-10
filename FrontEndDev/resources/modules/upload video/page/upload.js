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
    $scope.isPublic = false;
    $scope.isPublicLink = false;
    $scope.submit= function() {
        if ($scope.video.files && $scope.video.files.length) {
            if($scope.video.confidentiality == '0'){
                $scope.isPublic = false;
                $scope.isPublicLink = false;
            }else if($scope.video.confidentiality == '1'){
                $scope.isPublic = true;
                $scope.isPublicLink = true;
            }else{
                $scope.isPublic = false;
                $scope.isPublicLink = true;
            }
            video = {
                file: $scope.video.files[0],
                title: $scope.video.title,
                description: $scope.video.description,
                isPublic: $scope.isPublic,
                isPublicLink: $scope.isPublicLink
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
            if (video.files[0].size > constants.FILE_SIZE_ALLOWED) {
                moastr.error(constants.FILE_SIZE_ERROR, 'left right bottom');
            }
        }
    }
};
});