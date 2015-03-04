angular.module('test', ['ngRoute', 'angularFileUpload'])//https://github.com/danialfarid/angular-file-upload
    .config(function ($routeProvider,$locationProvider){
        $routeProvider.when("/test",{
            title: "LizTube - test",
            page: "Test",
            controller: 'FileUploadController',
            templateUrl: "test.html"
        });
    })
    .controller('FileUploadController', function($scope, $http, $upload) {
        $scope.uploadRate = 0;

        $scope.$watch('files', function () {
            $scope.upload($scope.files);
        });

        $scope.upload = function (files) {
            if (files && files.length) {
                var file = files[0];
                $upload.upload({
                    url: '/api/video/upload',
                    fields: {
                        title:"title",
                        description:'description',
                        isPublic:false,
                        isPublicLink:false
                    },
                    file: file
                }).progress(function (evt) {
                    $scope.uploadRate = parseInt(100.0 * evt.loaded / evt.total);
                }).success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' +
                    JSON.stringify(data));
                });
            }
        };

});