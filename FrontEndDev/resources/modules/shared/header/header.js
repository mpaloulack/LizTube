/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "liztube.upload.video",
    "ngRoute"
]).controller("headerCtrl", function($scope, $mdSidenav) {

    $scope.isLoading = false;
    $scope.videoLoading = false;
    $scope.uploadRate = 0;


    $scope.$on('loadingUploadVideoForHeader', function(event, video) {
        $scope.videoLoading = true;
        $scope.uploadRate = video;
        $scope.percent = video + "%";
    });

    $scope.$on('loadingStatusForHeader', function(event, bool) {
        $scope.isLoading= bool;
    });

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
