/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube.home",[
    "ngRoute"
]).config(function ($routeProvider){
    $routeProvider.when("/",{
        title: "LizTube - Accueil",
        page: "Home",
        controller: 'homeCtrl',
        templateUrl: "home.html"
    }).otherwise({
        redirectTo: '/404',
        title: "LizTube - 404",
        page: "404 : Page non trouvé",
        templateUrl:"404.html"
    });
}).controller("homeCtrl", function($scope) {

});
