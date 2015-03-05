/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.register",[
    "ngRoute", 
    "liztube.dataService.authService",
    'ngMessages'
]).controller("registerCtrl", function($scope, $location, authService, $window, $interval) {


	$scope.user={
        	firstname: '',
		    lastname: '',
		    pseudo: '',
		    password: '',
		    birthdate: '',
		    email: '',
    		isFemale: true
        };
    $scope.register= function() {
        console.log(user);
    };
    
    /**/
})
.config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/register",{
        title: "LizTube - Enregistrement",
        page: "Login",
        controller: 'registerCtrl',
        templateUrl: "register.html"
    });
});
