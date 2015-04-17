angular.module("liztube.updatepassword",[
    "ngRoute",
    "liztube.moastr",
    "liztube.dataService.userService",
    'ngMessages'
]).config(function ($routeProvider){
    $routeProvider.when("/updatepassword",{
        title: "LizTube - Mise à jour du mot de passe",
        page: "Mise à jour du mot de passe",
        controller: 'updatePasswordCtrl',
        templateUrl: "updatepassword.html"
    });
}).controller("updatePasswordCtrl", function($scope, $rootScope, userService, $location, moastr, constants) {

    $scope.errorUpdate = '';
    $scope.password = {
        newPassword : '',
        oldPassword : ''
    };
    $scope.verify = {
        password : ''
    };

    $scope.update = function () {
        $rootScope.$broadcast('loadingStatus', true);
        userService.updatePassword($scope.password).then(function () {
            $location.path('/profile');
        }, function () {
            moastr.error(constants.SERVER_ERROR, 'left right bottom');
        }).finally(function () {
            $scope.$emit('loadingStatus', false);
        });
    };

});