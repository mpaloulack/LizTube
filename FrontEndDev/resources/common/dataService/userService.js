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
        updateProfile : updateProfile,
        emailExistUpdate : emailExistUpdate,
        updatePassword : updatePassword
    };

    /**
     Get current user info
     **/
    function userProfile() {
        return baseUser().get();
    }

    /**
     PUT update  user info
     **/
    function updateProfile(user) {
        return baseUser().customPUT(user);
    }

    /**
     PUT update password
     **/
    function updatePassword(passwords){
        //return Restangular.one("user/password").patch(passwords);
        return baseUser().one("password").patch(passwords);
    }

    /**
     * POST check if email changed and if exist
    **/
    function emailExistUpdate(email) {
        var emailObj = {
            'value':email
        };
        return baseAuth().post('email', emailObj);
    }
});