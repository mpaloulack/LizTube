angular.module("liztube.toast",[
    "ngRoute"
]).controller("toastCtrl", function($scope, $rootScope, $mdToast) {

    $scope.message = '';
    $scope.position = '';

    $rootScope.$on('launchToast', function(event, toast) {
        $scope.message = toast.message;
        $scope.position = toast.position;
        showToast(toast.position);
        console.log("TOASTTTTTTTTTTTTTTT");
    });

   $scope.showToast = function(position){
        $mdToast.show({
            controller: 'toastCtrl',
            templateUrl: 'toast-template.html',
            hideDelay: 6000,
            position: position
        });
    };
    $scope.closeToast = function(){
        $mdToast.hide();
    };
});