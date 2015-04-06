/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "ngRoute"
]).controller("headerCtrl", function($scope, $mdSidenav) {

    $scope.isLoading = false;

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
