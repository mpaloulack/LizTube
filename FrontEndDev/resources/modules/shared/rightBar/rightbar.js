/**
 * Created by Youcef on 05/03/2015.
 */
angular.module("liztube.rightBar",[
    "liztube.userStatus"
]).controller('RightSideBarCtrl', function($scope, $mdSidenav) {
    $scope.close = function() {
        $mdSidenav('right').close();
    };
});
