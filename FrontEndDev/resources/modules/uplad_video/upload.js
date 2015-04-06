angular.module('liztube.upload.video', [
    'ngRoute',
    'angularFileUpload' //https://github.com/danialfarid/angular-file-upload
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/upload",{
        title: "LizTube - vidéo upload",
        page: "Upload",
        controller: 'FileUploadController',
        templateUrl: "upload.html"
    });
})
.controller('FileUploadController', function($scope, $http, $upload) {
    $scope.submit= function() {
        $scope.upload($scope.video.files, $scope.video.title, $scope.video.description);
    };
    $scope.upload = function (files, title, description) {
        if (files && files.length) {
            var file = files[0];
            $upload.upload({
                url: '/api/video/upload',
                fields: {
                    title: title,
                    description: description,
                    isPublic:false,
                    isPublicLink:false
                },
                file: file
            }).progress(function (evt) {
                $scope.$emit('loadingUploadVideo', parseInt(100.0 * evt.loaded / evt.total));
            }).success(function (data, status, headers, config) {
                console.log('file "' + config.file.name + '" uploaded. Response: ' +
                JSON.stringify(data));
            });
        }
    };
});