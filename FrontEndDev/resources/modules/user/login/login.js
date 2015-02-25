/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",["ngRoute"])
    .controller("loginCtrl", function($scope) {
    })
    .config(function ($routeProvider,$locationProvider){
        $routeProvider.when("/login",{
            title: "LizTube - Login",
            page: "Login",
            controller: 'loginCtrl',
            templateUrl: "login.html"
        });
    });

