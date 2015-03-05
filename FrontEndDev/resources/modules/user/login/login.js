/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",[
    "liztube.dataService.authService",
    "ngRoute"
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/login",{
        title: "LizTube - Connexion",
        page: "Connexion",
        controller: 'loginCtrl',
        templateUrl: "login.html"
    });
}).controller("loginCtrl", function($scope, $rootScope, $location, authService, $window){

    $scope.errorLogin = '';

    $scope.submit= function() {
        $rootScope.$broadcast('loadingStatus', true);
        authService.login($scope.login, $scope.password).then(function(){
            authService.currentUser().then(function(currentUser){
                $window.user = currentUser;
                $rootScope.$broadcast('userStatus', currentUser);
                $location.path('/');
            },function(){});
        },function(){
            $scope.errorLogin ="Error login";
        }).finally(function(){
            $rootScope.$broadcast('loadingStatus', false);
        });
    };
});

