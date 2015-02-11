/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube",[
    "ngMaterial",
    "liztube.home",
    "liztube.partial",
    "ngRoute"
])
    .config(function ($routeProvider,$locationProvider){
        $locationProvider.html5Mode(true);
    })
    .run(function($rootScope) {
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
            }
        });
    });
