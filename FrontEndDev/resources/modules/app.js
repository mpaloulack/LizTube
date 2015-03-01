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
        $mdThemingProvider.definePalette('liztubePalette', {
            '50': 'ffebee',
            '100': 'ffcdd2',
            '200': 'ef9a9a',
            '300': 'e57373',
            '400': 'ef5350',
            '500': 'f44336',
            '600': 'e53935',
            '700': 'd32f2f',
            '800': 'c62828',
            '900': 'b71c1c',
            'A100': 'ff8a80',
            'A200': 'ff5252',
            'A400': 'ff1744',
            'A700': 'd50000',
            'contrastDefaultColor': 'light',    // whether, by default, text (contrast)
                                                // on this palette should be dark or light
            'contrastDarkColors': ['50', '100', //hues which contrast should be 'dark' by default
                '200', '300', '400', 'A100'],
            'contrastLightColors': undefined    // could also specify this if default was 'dark'
        });
        $mdThemingProvider.theme('default')
            .primaryPalette('amazingPaletteName')
            .accentPalette('blue');
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
