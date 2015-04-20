/**
 * Created by maxime on 17/03/2015.
 */
angular.module("liztube.profile",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/profil",{
        title: "LizTube - Mon profil",
        page: "Profil",
        controller: 'profileCtrl',
        templateUrl: "profile.html"
    });
}).controller("profileCtrl", function($scope, $rootScope, userService, $window, $location, moastr, constants) {
    $scope.errorUpdate = '';

    /**
     * Get user profile
     */
    $scope.getUserProfile = function(){
        userService.userProfile().then(function(user){
            $scope.user = user;
            var date = new Date($scope.user.birthdate);
            $scope.user.birthdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        },function(){
            moastr.error(constants.SERVER_ERROR, 'left right bottom');
        });
    };

});