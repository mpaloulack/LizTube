/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",[
    "liztube.dataService.authService",
    "liztube.moastr",
    "ngRoute"
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/login",{
        title: "LizTube - Connexion",
        page: "Connexion",
        controller: 'loginCtrl',
        templateUrl: "login.html"
    });
})
.controller("loginCtrl", function($scope, $rootScope, $location, authService, $window, moastr){

    $scope.errorLogin = '';

    $scope.submit= function() {
        $rootScope.$broadcast('loadingStatus', true);
        authService.login($scope.login, $scope.password).then(function(){
            authService.currentUser().then(function(currentUser){
                $window.user = currentUser;
                $rootScope.$broadcast('userStatus', currentUser);
                $location.path('/');
            },function(){/*toastError();*/});
        },function(){
            moastr.error('tetstststs');
        }).finally(function(){
            $rootScope.$broadcast('loadingStatus', false);
            
        });
    };

    /*var toastError = function(){
        $mdToast.show({
            controller: 'toastCtrl',
            templateUrl: 'toast-template.html',
            hideDelay: 6000,
            position: "left right bottom"
        });
    };*/
});

