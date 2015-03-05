/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",[
    "liztube.dataService.authService",
    "ngRoute",
    'ngMessages'
]).controller("loginCtrl", function($scope, $rootScope, $location, authService, $window, $interval){

    $scope.submit= function() {
        $rootScope.$broadcast('loadingStatus', true);
        $interval(function(){
            console.log($scope.loaderLogin);
            authService.login($scope.login, $scope.password).then(function(){
            console.log("success");
            authService.currentUser().then(function(currentUser){
                console.log("currentUser success " +currentUser.pseudo);
                $window.user = currentUser;
                $rootScope.$broadcast('userStatus', currentUser);
            },function(){
                console.log("currentUser fail");
            });
            $location.path('/');
            },function(){
                console.log("fail");
                $scope.errorLogin ="Error login";
            }).finally(function(){
                $rootScope.$broadcast('loadingStatus', false);
            });
        },1000,1);
    };
})
.config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/login",{
        title: "LizTube - Connexion",
        page: "Connexion",
        controller: 'loginCtrl',
        templateUrl: "login.html"
    });
});

