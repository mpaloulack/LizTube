/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.register",[
    "ngRoute", 
    "liztube.dataService.authService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/register",{
        title: "LizTube - Inscription",
        page: "Inscription",
        controller: 'registerCtrl',
        templateUrl: "register.html"
    });
}).controller("registerCtrl", function($scope, $location, authService, $window, $interval) {
	$scope.user={
        	firstname: '',
		    lastname: '',
		    pseudo: '',
		    birthdate: '',
		    email: '',
            password: '',
            passwordConfirme: '',
    		isFemale: true
        };
    $scope.register= function() {
        console.log(user);
    };

});
