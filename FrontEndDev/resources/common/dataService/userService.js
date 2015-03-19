/**
 User data service : to get/set data from the API

 **/

angular.module('liztube.dataService.userService', [
    'restangular'
]).factory('userService', function (Restangular) {
    function baseUser(){
        return Restangular.one("user");
    }

    return {
        userProfile : userProfile,
        updateProfile : updateProfile
    };

    /**
     Get current user info
     **/
    function userProfile() {
        return baseUser().one('infoProfile').get();
    }

    /**
     Post update  user info
     **/
    function updateProfile(user) {
        return baseUser().post('infoProfile', user);
    }
});