angular.module('liztube.videos.channel', [
    'ngRoute',
    "liztube.videos"
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/channel/:user/:id",{
        title:"LizTube - vidéos de ",
        page: "vidéos de ",
        controller: 'userChannelCtrl',
        templateUrl: "userChannel.html"
    });
}).controller('userChannelCtrl', function($scope, $window, $routeParams) {
    $scope.pageTitle = "vidéos de " + $routeParams.user;
    $scope.orderBy = "mostrecent";
    $scope.page = "";
    $scope.pagination = "";
    $scope.userId = $routeParams.id;
    $scope.q = "";
    $scope.for = "home";
});