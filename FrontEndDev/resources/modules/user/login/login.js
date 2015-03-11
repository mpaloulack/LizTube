/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",[
    "liztube.dataService.authService",
    "liztube.moastr",
    "ngRoute"
]).config(function ($routeProvider){
    $routeProvider.when("/login",{
        title: "LizTube - Connexion",
        page: "Connexion",
        controller: 'loginCtrl',
        templateUrl: "login.html"
    });
})
.controller("loginCtrl", function($scope, $rootScope, $location, authService, $window, moastr){

    $scope.errorLogin = '';
        $rootScope.$broadcast('launchToast', {
            'message': "test2",
            'position' : "left right bottom"
        });
    $scope.submit= function() {
        $rootScope.$broadcast('loadingStatus', true);
        authService.login($scope.login, $scope.password).then(function(){
            authService.currentUser().then(function(currentUser){
                $window.user = currentUser;
                $rootScope.$broadcast('userStatus', currentUser);
                $location.path('/');
            },function(){
                moastr.error('An unexpected error occured. If the problem persists please contact the administrator.');
            });
        },function(){
            moastr.error('Bad credentials');
        }).finally(function(){
            $rootScope.$broadcast('loadingStatus', false);
        });
    };
});

