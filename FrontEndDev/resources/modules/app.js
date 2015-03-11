/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube",[
    "liztube.themes",
    'liztube.utils',
    "liztube.menu",
    "liztube.home",
    "liztube.user",
    "liztube.partial",
    "ngRoute",
    'ngMessages',
    'test'
]).config(function ($routeProvider,$locationProvider,RestangularProvider){
    $locationProvider.html5Mode(true);
    RestangularProvider.setBaseUrl('api/');
}).controller('mainCtrl', function ($scope) {
    $scope.$on('loadingStatus', function (event, bool) {
        $scope.$broadcast('loadingStatusForHeader', bool);
    });
    $scope.$on('userStatus', function(event, user) {
        $scope.$broadcast('userIsConnected', user);
    });
}).run(function($rootScope) {
    $rootScope.$on('$routeChangeStart', function(event, current, previous) {
        if (current.$$route && current.$$route.resolve) {
            // Show a loading message until promises are not resolved
            $rootScope.isViewLoading = true;
        }
    });
    $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
        $rootScope.isViewLoading = false;
        if (current.hasOwnProperty('$$route')) {
            $rootScope.title = current.$$route.title;
            $rootScope.page = current.$$route.page;
        }
    });
});
