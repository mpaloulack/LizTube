/**
 * Created by Youcef on 04/03/2015.
 */
angular.module("liztube.userStatus",[

]).controller("connectedCtrl", function($scope,$rootScope,$window) {
    $rootScope.$on('userStatus', function(event, user) {
        $scope.pseudo = user.pseudo;
        $scope.userConnected = true;
    });
    if(! $scope.pseudo){
        if($window.user.pseudo){
            $scope.pseudo = $window.user.pseudo;
            $scope.userConnected = true;
        }else{
            $scope.userConnected = false;
        }
    }
    /*$scope.logOut = function(){
        authService.logout().then(function(){
            window.location.reload();
            resolve(result);
        }, function(){
            console.log("fail Logout");
            reject(false);
        });
    };*/
}).directive('isConnected', function () {
    return {
        restrict: 'E',
        controller: 'connectedCtrl',
        templateUrl: "connected.html",
        scope: {
            type: "@"
        }
    };
});
