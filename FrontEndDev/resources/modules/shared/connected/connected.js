/**
 * Created by Youcef on 04/03/2015.
 */
angular.module("liztube.userStatus",[
    "liztube.dataService.authService",
    "ngRoute"
]).controller("connectedCtrl", function($scope,$rootScope,$window, authService,$location) {
    $rootScope.$on('userStatus', function(event, user) {
        if(_.isUndefined(user)){
            $scope.pseudo = '';
            $scope.userConnected = false;
        }else {
            $scope.pseudo = user.pseudo;
            $scope.userConnected = true;
        }
    });
    if(!$scope.pseudo){
        if($window.user.pseudo){
            $scope.pseudo = $window.user.pseudo;
            $scope.userConnected = true;
        }else{
            $scope.userConnected = false;
        }
    }
    $scope.logOut = function(){
        authService.logout().then(function(){
            $rootScope.$broadcast('userStatus', undefined);
            $location.path("/");
        }, function(){

        });
    };
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
