/**
 * Created by maxime on 09/04/2015.
 */
angular.module("liztube.updatepassword",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/updatepassword",{
        title: "LizTube - Mise à jour du mot de passe",
        page: "Mise à jour du mot de passe",
        controller: 'updatePasswordCtrl',
        templateUrl: "updatepassword.html"
    });
}).controller("updatePasswordCtrl", function($scope, $rootScope, userService, $location, moastr, constants) {

    $scope.errorUpdate = '';

    $scope.update = function () {
        $rootScope.$broadcast('loadingStatus', true);
        userService.updatePassword($scope.password).then(function () {
            $location.path('/profile');
        }, function () {
            moastr.error(constants.SERVER_ERROR);
        }).finally(function () {
            $rootScope.$broadcast('loadingStatus', false);
        });
    };

})/*.directive('passwordVerify', function() {
    return {
        require: "ngModel",
        scope: {
            passwordVerify: '='
        },
        link: function(scope, element, attrs, ctrl) {
            scope.$watch(function() {
                var combined;

                if (scope.passwordVerify || ctrl.$viewValue) {
                    combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                }
                return combined;
            }, function(value) {
                if (value) {
                    ctrl.$parsers.unshift(function(viewValue) {
                        var origin = scope.passwordVerify;
                        if (origin !== viewValue) {
                            ctrl.$setValidity("passwordVerify", false);
                            return undefined;
                        } else {
                            ctrl.$setValidity("passwordVerify", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
})*/;