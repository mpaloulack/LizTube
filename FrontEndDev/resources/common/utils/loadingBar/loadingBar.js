/**
 * Created by Youcef on 05/03/2015.
 */
angular.module("liztube.loading",[

]).controller("loadingCtrl", function($scope,$rootScope) {

    $scope.isLoading = false;

    $rootScope.$on('loadingStatus', function(event, bool) {
        $scope.isLoading= bool;
    });

});
