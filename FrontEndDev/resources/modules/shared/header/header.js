/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "ngRoute"
]).controller("headerCtrl", function($scope, $timeout, $mdSidenav) {
    $scope.toggleRight = function() {
        $mdSidenav('right').toggle();
    };

}).controller('RightSideBarCtrl', function($scope, $mdSidenav) {
        $scope.close = function() {
            $mdSidenav('right').close();
        };
}).directive('header', function () {
    return {
        restrict: 'E',
        controller: 'headerCtrl',
        templateUrl: "header.html"
    };
});
