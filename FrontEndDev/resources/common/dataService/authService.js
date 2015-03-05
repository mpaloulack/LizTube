/**
Authentication data service : to get/set data from the API

**/

angular.module('liztube.dataService.authService', [
    'restangular'
]).factory('authService', function (Restangular) {
    var RestangularDefault = Restangular.withConfig(function(RestangularConfigurer) {
        RestangularConfigurer.setBaseUrl('/');
    });

    var RestangularForAuth = Restangular.withConfig(function(RestangularConfigurer) {
        RestangularConfigurer.setBaseUrl('/api/auth');
    });

    return {
        login: login,
        currentUser: currentUser
    };

    /**
    Get current connected user
    **/
    function currentUser() {
        return Restangular.one('currentProfil').get();
    }

    function login(username, password) {
        return RestangularDefault.one('login')
        .customPOST("username="+username+"&password="+password,
            undefined,
            undefined,
            {'Content-Type': 'application/x-www-form-urlencoded'});
    }
    function logout(){
        return RestangularDefault.one('logout');
    }
});