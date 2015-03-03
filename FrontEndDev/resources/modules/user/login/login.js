/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",["ngRoute", "liztube.dataService.authService"])
    .controller("loginCtrl", function($scope, $location, authService, $window) {

        $scope.submit= function() {
            authService.login($scope.login, $scope.password).then(function(){
                console.log("success");
                authService.currentUser().then(function(currentUser){
                    console.log("currentUser success " +currentUser.pseudo);
                    $window.user = currentUser;
                },function(){
                    console.log("currentUser fail");
                });
                $location.path('/');
            },function(){
                console.log("fail");
                $scope.errorLogin ="Error login";
            });
        };
        
        /**/
    })
    .config(function ($routeProvider,$locationProvider){
        $routeProvider.when("/login",{
            title: "LizTube - Login",
            page: "Login",
            controller: 'loginCtrl',
            templateUrl: "login.html"
        });
    });

