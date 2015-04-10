/**
 * Created by maxime on 17/03/2015.
 */
angular.module("liztube.profile",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages',
    'ui.gravatar'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/profile",{
        title: "LizTube - My profile",
        page: "Profile",
        controller: 'profileCtrl',
        templateUrl: "profile.html"
    });
}).config([
    'gravatarServiceProvider', function(gravatarServiceProvider) {
        gravatarServiceProvider.defaults = {
            size     : 150,
            "default": 'mm'  // Mystery man as default for missing avatars
        };
    }
]).controller("profileCtrl", function($scope, $rootScope, userService, $window, $location, moastr, constants) {

    userService.userProfile().then(function(user){
        $scope.user = user;

        var date = new Date($scope.user.birthdate);

        $scope.user.birthdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        }, function(){
        moastr.error(constants.SERVER_ERROR,'left right bottom');

    });

   $scope.errorUpdate = '';


})/*.directive('emailValidation', function(authService, moastr, $scope) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.emailValid = function(modelValue, viewValue) {
                var value = modelValue || viewValue;

                return authService.emailExist(value).then(function(bool){
                    if (bool && value != $scope.user.email) {
                        ctrl.$setValidity("emailValidation", false);
                        return undefined;
                    }else{
                        ctrl.$setValidity("emailValidation", true);
                        return viewValue;
                    }
                    return true;
                },function(){
                    moastr.error(constants.SERVER_ERROR);
                });
            };
        }
    };
})*/;