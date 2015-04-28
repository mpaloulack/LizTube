/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube.search",[
'ngRoute'
]).config(function ($routeProvider){
    $routeProvider.when("/search=:search",{
        title: "LizTube - Recherche",
        page: "Recherche",
        controller: 'searchCtrl',
        templateUrl: "search.html"
    });
}).controller("searchCtrl", function($scope, $routeParams) {
    $scope.pageTitle = "Vidéos les plus récentes";
    $scope.orderBy = "mostrecent";
    $scope.page = "";
    $scope.pagination = "";
    $scope.userId = "";
    $scope.q = $routeParams.search;
    $scope.for = "home";
});
