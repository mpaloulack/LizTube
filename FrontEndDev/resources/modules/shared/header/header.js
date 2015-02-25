/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[])
    .controller("headerCtrl", function($scope, $timeout, $mdSidenav, $log) {
        $scope.toggleRight = function() {
            $mdSidenav('right').toggle();
        };

    })
    .controller('RightSideBarCtrl', function($scope, $timeout, $mdSidenav, $log) {
        $scope.close = function() {
            $mdSidenav('right').close();
        };
    })
    .directive('header', function () {
        return {
            restrict: 'E',
            controller: 'headerCtrl',
            templateUrl: "header.html"
        };
    });
