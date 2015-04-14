angular.module('liztube.videos.user', [
    'ngRoute',
    "liztube.videos"
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/videos-user",{
        title: "LizTube - Mes vidéos",
        page: "Mes vidéos",
        controller: 'userVideosCtrl',
        templateUrl: "myVideos.html"
    });
}).controller('userVideosCtrl', function($scope, constants, $window) {
    $scope.orderBy = "mostrecent";
    $scope.page = "";
    $scope.pagination = "";
    $scope.userId = $window.user.id;
    $scope.q = "";
});