/**
 * Created by Youcef on 11/02/2015.
 */
angular.module("liztube.header",[
    "liztube.userStatus",
    "ngRoute",
    "liztube.upload.video"
]).controller("headerCtrl", function($scope, $mdSidenav, constants) {

    $scope.notification = 0;
    $scope.showNotification = false;
    $scope.isLoading = false;
    $scope.noNotification = constants.NO_NOTIFICATIONS_FOUND;

    /**
     * Add a notification
     */
    $scope.$on('addNotificationForHeader', function(event, bool) {
        $scope.showNotification = bool;
        $scope.notification = $scope.notification + 1;
        $scope.noNotification = "";
    });

    /**
     * Remove a notification
     */
    $scope.$on('removeNotificationForHeader', function(event, bool) {
        if($scope.notification > 0){
            $scope.notification = $scope.notification - 1;
            if($scope.notification === 0){
                $scope.showNotification = false;
                $scope.noNotification = constants.NO_NOTIFICATIONS_FOUND;
            }
        }
    });

    /**
     * Set loading status
     */
    $scope.$on('loadingStatusForHeader', function(event, bool) {
        $scope.isLoading= bool;
    });

    /**
     * Open or close (toggle) side nav part
     */
    $scope.toggleRight = function() {
        $mdSidenav('right').toggle();
    };

    $scope.closeRightBar = function() {
        $mdSidenav('right').close();
    };

}).directive('header', function () {
    return {
        restrict: 'E',
        controller: 'headerCtrl',
        templateUrl: "header.html"
    };
});
