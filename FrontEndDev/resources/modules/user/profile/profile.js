/**
 * Created by maxime on 08/04/2015.
 */
angular.module("liztube.profile",[
    "liztube.moastr",
    "liztube.dataService.userService",
    "liztube.videos",
    'ngMessages'
]).config(function ($routeProvider){
    $routeProvider.when("/profil",{
        title: "LizTube - Profil",
        page: "Profil",
        controller: 'profileCtrl',
        templateUrl: "profile.html"
    });
}).controller("profileCtrl", function($scope, $rootScope, userService, $location, moastr, constants, $window) {
    $scope.pageTitle = "Mes vidéos";
    $scope.orderBy = "mostrecent";
    $scope.page = "1";
    $scope.pagination = "20";
    $scope.userId = $window.user.id;
    $scope.q = "";
    $scope.for = "user";

    $scope.errorUpdate = '';
    $scope.isDisable = true;
    $scope.profilTitle = "Profil";

    $scope.enableUpdateProfil = function(){
        $scope.profilTitle = "Mise à jour du profil";
        $scope.isDisable = false;
    };

    var disableUpdateProfil= function(){
        $scope.profilTitle = "Profil";
        $scope.isDisable = true;
    };

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


    /**
     * Update user profile
     */
        $scope.update = function () {
            $rootScope.$broadcast('loadingStatus', true);

            userService.updateProfile($scope.user).then(function () {
                moastr.successMin(constants.UPDATE_PROFILE_OK, 'top right');
            }, function () {
                moastr.error(constants.SERVER_ERROR, 'left right bottom');
            }).finally(function () {
                $rootScope.$broadcast('loadingStatus', false);
                disableUpdateProfil();
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
                    moastr.error(constants.SERVER_ERROR, 'left right bottom');
                });
            };
        }
    };
});
