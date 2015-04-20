describe('liztube.updateprofile', function() {
    var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
    };

	beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.updateprofile'));
    beforeEach(module('liztube.dataService.userService'));

    var $scope, $rootScope, $location, userService, $q, constants, $window, createController;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        UPDATE_PROFILE_OK: "Votre profil à bien était mis à jour"
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_, _$location_, _$route_, _userService_, _$window_, _$q_) {
        $rootScope =_$rootScope_;
        $location = _$location_;
        route = _$route_;
        userService = _userService_;
        $window= _$window_;
        $q = _$q_;
    }));

    var moastr = {
        error: function(message){
            return message;
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('updateProfileCtrl', {
                '$scope': $scope,
                'constants': mockConstants,
                'moastr' : moastr
            });
        };
    }));

    describe('UpdateProfile route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('updateprofile.html')
                    .respond(200);
            }));

        it('should load the updatepassord page on successful load of /updatepassword', function() {
            $location.path('/majprofil');
            $rootScope.$digest();
            expect(route.current.controller).toBe('updateProfileCtrl');
            expect(route.current.title).toBe('LizTube - Mise à jour du profil');
            expect(route.current.page).toBe('Mise à jour du profil');
        });
    });

    describe('UpdatepProfileCtrl', function() {

        beforeEach(function(){
            createController();
        });

    	describe('Get user Info', function() {
    		var userInfoPromise, user;

			beforeEach(function(){
                user={
                    firstname: 'firstname',
                    lastname: 'lastname',
                    email: 'email@test.fr',
                    birthdate: 703429875000,
                    isfemale: false
                };
                $scope.user=null;
                userInfoPromise = $q.defer();
	            spyOn(userService, 'userProfile').and.returnValue(userInfoPromise.promise);
                $scope.getUserProfile();
	    	});

	    	beforeEach(function(){
	            spyOn($location,'path').and.callThrough();
	            spyOn(moastr, 'error').and.callThrough();
	        });

	    	it('should return an error message', function(){
	            changePromiseResult(userInfoPromise, "failed");
	            expect(moastr.error).toHaveBeenCalledWith(mockConstants.SERVER_ERROR, 'left right bottom');
	        });

	        it('should be a successful get user info', function() {
				changePromiseResult(userInfoPromise, "resolve", user);
                expect($scope.user).toEqual(user);
                expect($scope.user.birthdate).toEqual(new Date(1992, 3, 16));
			});

    	});

        describe('Update user info', function() {
            var submitPromise, user;

            beforeEach(function(){
                user={
                    firstname: 'firstname',
                    lastname: 'lastname',
                    email: 'email@test.fr',
                    birthdate: 703429875000,
                    isfemale: false
                };
            });

            beforeEach(function(){
                submitPromise = $q.defer();
                spyOn(userService, 'updateProfile').and.returnValue(submitPromise.promise);
                $scope.update();
            });

            beforeEach(function(){
                spyOn($location,'path').and.callThrough();
                spyOn(moastr, 'error').and.callThrough();
                //spyOn(moastr, 'success').and.callThrough();
                userService.updateProfile(user);
            });

            it('should return an error message', function(){
                changePromiseResult(submitPromise, "failed");
                expect(moastr.error).toHaveBeenCalledWith(mockConstants.SERVER_ERROR,'left right bottom');
            });

            it('should be a successful update', function() {
                changePromiseResult(submitPromise, "resolve");
                //expect(moastr.successMin).toHaveBeenCalledWith(mockConstants.UPDATE_PASSWORD_OK, 'top right');
                expect($location.path).toHaveBeenCalledWith('/profil');
            });
        });
    });

});