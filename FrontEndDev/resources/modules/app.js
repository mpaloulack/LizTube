/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube",[
    "ngMaterial",
    "liztube.menu",
    "liztube.home",
    "liztube.user",
    "liztube.partial",
    "ngRoute"
])
    .config(function ($routeProvider,$locationProvider,$mdThemingProvider){
        $locationProvider.html5Mode(true);
        //Theme material design.
        $mdThemingProvider.theme('default')
            .accentPalette('green', {
                'default': '600'
            });
        $mdThemingProvider.theme('navbar')
            .primaryPalette('grey', {
                'default': '200'
            })
            .accentPalette('blue-grey', {
                'default': '500'
            });
        $mdThemingProvider.theme('sub-bar')
            .primaryPalette('grey', {
                'default': '600'
            });
        $mdThemingProvider.setDefaultTheme('default');
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
                $rootScope.page = current.$$route.page;
            }
        });
    });
