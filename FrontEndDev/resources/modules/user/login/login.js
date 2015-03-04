/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.login",[
    "ngRoute", 
    "liztube.dataService.authService",
    'ngMessages'
]).controller("loginCtrl", function($scope, $location, authService, $window, $interval) {

    $scope.loaderLogin= false;

    $scope.submit= function() {
        $scope.loaderLogin= true;

        $interval(function(){
            console.log($scope.loaderLogin);
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
            }).finally(function(){
                $scope.loaderLogin= false;
            });
        },1000,1);
        
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

