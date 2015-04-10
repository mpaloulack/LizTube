/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "ngRoute",
    "liztube.upload.video"
]).controller("headerCtrl", function($scope, $mdSidenav) {

    $scope.notification = 0;
    $scope.showNotification = false;
    $scope.$on('addNotificationForHeader', function(event, bool) {
        $scope.showNotification = bool;
        $scope.notification = $scope.notification + 1;
    });

    $scope.$on('removeNotificationForHeader', function(event, bool) {
        $scope.notification = $scope.notification - 1;
        if($scope.notification === 0){
            $scope.showNotification = false;
        }
    });

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
