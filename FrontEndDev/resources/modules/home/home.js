/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube.home",["ngRoute"])
    .controller("homeCtrl", function($scope, $timeout, $mdSidenav, $log) {
        $scope.toggleRight = function() {
            $mdSidenav('right').toggle()
                .then(function(){
                    $log.debug("toggle RIGHT is done");
                });
        };

    })
    .controller('RightSideBarCtrl', function($scope, $timeout, $mdSidenav, $log) {
        $scope.close = function() {
            $mdSidenav('right').close()
                .then(function(){
                    $log.debug("close RIGHT is done");
                });
        };
    })
    .config(function ($routeProvider,$locationProvider){
        $routeProvider.when("/",{
            title: "LizTube - Home",
            controller: 'homeCtrl',
            templateUrl: "home.html"
        }).otherwise({
            redirectTo: '/404',
            title: "LizTube - Home",
            templateUrl:"404.html"
        });
        $locationProvider.html5Mode(true);
    });
