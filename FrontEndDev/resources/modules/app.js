/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube",[
    "ngMaterial",
    "ngRoute"
])
    .controller("indexCtrl", function($rootScope){

    })
    .config(function ($routeProvider,$locationProvider){
        $routeProvider.when("/",{
            title: "Home",
            controller: 'indexCtrl',
            templateUrl: "../app/dist/partials/home.html"
        }).otherwise({
            redirectTo: '/404',
            templateUrl:"../app/dist/partials/404.html"
        });
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
            $rootScope.title = current.$$route.title;
            $rootScope.isViewLoading = false;
        });
    });
