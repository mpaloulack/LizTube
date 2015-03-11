/**
 * Created by Youcef on 05/03/2015.
 */
angular.module("liztube.rightBar",[
    "liztube.userStatus"
]).controller('RightSideBarCtrl', function($scope, $mdSidenav) {
    $scope.closeRightBar = function() {
        $mdSidenav('right').close();
    };
});
