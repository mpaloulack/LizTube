/**
 * Created by Youcef on 28/01/2015.
 */
angular.module("liztube.home",[
    "ngRoute"
]).controller("homeCtrl", function($scope) {

})
.config(function ($routeProvider,$locationProvider){
    $routeProvider.when("/",{
        title: "LizTube - Home",
        page: "Home",
        controller: 'homeCtrl',
        templateUrl: "home.html"
    }).otherwise({
        redirectTo: '/404',
        title: "LizTube - 404",
        page: "404 page non trouvé",
        templateUrl:"404.html"
    });
});
