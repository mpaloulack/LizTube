/**
 * Created by Youcef on 26/02/2015.
 */
angular.module("liztube.register",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.authService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/register",{
        title: "LizTube - Inscription",
        page: "Inscription",
        controller: 'registerCtrl',
        templateUrl: "register.html"
    });
}).controller("registerCtrl", function($scope, $rootScope, authService, $location) {

        $scope.errorRegister = '';

        $scope.register = function () {
            $rootScope.$broadcast('loadingStatus', true);
            authService.register($scope.user).then(function () {
                $location.path('/login');
            }, function () {
                $scope.errorRegister = "Error Register";
                moastr.error('An unexpected error occured. If the problem persists please contact the administrator.');
            }).finally(function () {
                $rootScope.$broadcast('loadingStatus', false);
            });
        };

}).directive('passwordVerify', function() {
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
}).directive('emailValidation', function(authService) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.emailValid = function(modelValue, viewValue) {
                var value = modelValue || viewValue;

                return authService.emailExist(value).then(function(bool){
                    if (bool) {
                        ctrl.$setValidity("emailValidation", false);
                        return undefined;
                    }else{
                        ctrl.$setValidity("emailValidation", true);
                        return viewValue;
                    }
                    return true;
                },function(){
                    conosle.log("error check email");
                });
            };
        }
    };
}).directive('pseudoValidation', function(authService) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.pseudoValid = function(modelValue, viewValue) {
                var value = modelValue || viewValue;

                return authService.pseudoExist(value).then(function(bool){
                    if (bool) {
                        ctrl.$setValidity("pseudoValidation", false);
                        return undefined;
                    }else{
                        ctrl.$setValidity("pseudoValidation", true);
                        return viewValue;
                    }
                    return true;
                },function(){
                    conosle.log("error check pseudo");
                });
            };
        }
    };
});
