/**
 * Created by maxime on 08/04/2015.
 */
angular.module("liztube.updateprofile",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/updateprofile",{
        title: "LizTube - Mise à jour du profil",
        page: "Mise à jour du profil",
        controller: 'updateProfileCtrl',
        templateUrl: "updateprofile.html"
    });
}).controller("updateProfileCtrl", function($scope, $rootScope, userService, $location, moastr, constants) {

    userService.userProfile().then(function(user){
        $scope.user = user;

        var date = new Date($scope.user.birthdate);

        $scope.user.birthdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());},
        function(){
        moastr.error('An unexpected error occured. If the problem persists please contact the administrator.');
    });

    $scope.errorUpdate = '';



    $scope.update = function () {
        $rootScope.$broadcast('loadingStatus', true);
        userService.updateProfile($scope.user).then(function () {

        }, function () {
            moastr.error(constants.SERVER_ERROR);
        }).finally(function () {
            $rootScope.$broadcast('loadingStatus', false);
        });
    };

}).directive('emailValidation', function(userService, moastr) {
    return {
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            ctrl.$asyncValidators.emailValid = function(modelValue, viewValue) {
                var value = modelValue || viewValue;

                return userService.emailExistUpdate(value).then(function(bool){
                    if (bool) {
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
});
