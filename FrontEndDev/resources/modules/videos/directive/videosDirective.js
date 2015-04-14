angular.module("liztube.videos",[
    "liztube.dataService.videosService",
    "ngRoute"
]).controller("videosCtrl", function($scope, constants, videosService) {

    $scope.pamaeters = {};

    $scope.$watch("params",function(){
        if(!_.isUndefined($scope.getParams().orderBy) && $scope.getParams().orderBy !== ""){
            $scope.orderBy = $scope.getParams().orderBy;
        }else{
            $scope.orderBy = "q";
        }
        if(!_.isUndefined($scope.getParams().page) && $scope.getParams().page !== ""){
            $scope.pamaeters.page = $scope.getParams().page;
        }
        if(!_.isUndefined($scope.getParams().pagination) && $scope.getParams().pagination !== ""){
            $scope.pamaeters.pagination = $scope.getParams().pagination;
        }
        if(!_.isUndefined($scope.getParams().user) && $scope.getParams().user !== ""){
            $scope.pamaeters.user = $scope.getParams().user;
        }
        if(!_.isUndefined($scope.getParams().q) && $scope.getParams().q !== ""){
            $scope.pamaeters.q = $scope.getParams().q;
        }
        console.log($scope.pamaeters);
        videosService.getVideos($scope.orderBy, $scope.pamaeters).then(function(videos){

        },function(){

        }).finally(function(){

        });
    });
}).directive('liztubeVideos', function () {
    return {
        restrict: 'E',
        controller: 'videosCtrl',
        templateUrl: "videosDirective.html",
        replace: true,
        scope: {
            orderBy: "@",
            page: "@",
            pagination: "@",
            user: "@",
            q: "@"
        },
        link: function(scope, element, attrs, videosCtrl) {
            scope.params = {
                orderBy: scope.orderBy,
                page: scope.page,
                pagination: scope.pagination,
                user: scope.user,
                q: scope.q
            };
            scope.getParams = function(){
                return scope.params;
            };
        }
    };
});

