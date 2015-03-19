/**
 * Created by maxime on 17/03/2015.
 */
angular.module("liztube.profile",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages'
]).config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/profile",{
        title: "LizTube - My profile",
        page: "Profile",
        controller: 'profileCtrl',
        templateUrl: "profile.html"
    });
}).controller("profileCtrl", function($scope, $rootScope, userService, $window, $location, moastr, constants) {

    userService.userProfile().then(function(user){
        $scope.user = user;

        var date = new Date($scope.user.birthdate);

        $scope.user.birthdate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
        console.log($scope.user.birthdate);    }, function(){
        moastr.error('An unexpected error occured. If the problem persists please contact the administrator.');
    });

   $scope.errorUpdate = '';



      $scope.update = function () {
          console.log($scope.user.firstname);
        $rootScope.$broadcast('loadingStatus', true);
        userService.updateProfile($scope.user).then(function () {
            //$location.path('/profile');
            if($scope.user.password === null){
                $scope.user.password = "";
            }
        }, function () {
            moastr.error(constants.SERVER_ERROR);
        }).finally(function () {
            $rootScope.$broadcast('loadingStatus', false);
        });
    };

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