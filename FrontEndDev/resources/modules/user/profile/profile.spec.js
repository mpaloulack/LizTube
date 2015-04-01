~describe('liztube.profile', function() {
	var changePromiseResult = function (promise, status, value) {
	    if (status === 'resolve')
	        promise.resolve(value);
	    else
	        promise.reject(value);
	    $rootScope.$digest();
	};

	beforeEach(module('liztube.profile'));
	beforeEach(module('liztube.dataService.userService'));

	var $scope, $rootScope, $location, userService, authService, $q, constants, $window, createController;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.'
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_, _$location_, _userService_, _$window_, _$q_, _authService_) {
		$rootScope =_$rootScope_;
		$location = _$location_;
		userService = _userService_;
		$window= _$window_;
	    $q = _$q_;
	    $authService = _authService_;
	}));

	var moastr = {
        error: function(message){
            return message;
        }
    };

	beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('registerCtrl', {
             '$scope': $scope,
             'moastr' : moastr
         });
        };
	}));

	beforeEach(function(){
        createController();
    });

    describe('GetProfile', function() {
    	
    });

    describe('UpdateProfile', function() {
    	var currentProfilPromise;

    	beforeEach(function(){
            currentProfilPromise = $q.defer();
            spyOn(authService, 'currentUser').and.returnValue(currentProfilPromise.promise);
            currentUserResponse = {
                pseudo:'max',
                roles:['ROLE_ADMIN']
            };
        });
    });


	describe('Password verify', function() {
		
	});

	describe('email validation', function() {
		var emailVerifyPromise;

		beforeEach(function(){
	        registerPromise = $q.defer();
            spyOn(authService, 'emailExist').and.returnValue(emailVerifyPromise.promise);
    	});

	});
});