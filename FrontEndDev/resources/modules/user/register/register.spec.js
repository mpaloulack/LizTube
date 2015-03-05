describe('liztube.register', function() {

	var changePromiseResult = function (promise, status, value) {
	    if (status === 'resolve')
	        promise.resolve(value);
	    else
	        promise.reject(value);
	    $rootScope.$digest();
	};

	beforeEach(module('liztube.register'));
	beforeEach(module('liztube.dataService.authService'));

	var $scope, $rootScope, $location, authService, $window, $q;

	beforeEach(inject(function (_$rootScope_, _$location_, _authService_, _$window_, _$q_) {
		$rootScope =_$rootScope_;
		$location = _$location_;
		authService = _authService_;
		$window= _$window_;
	    $q = _$q_;
	}));

	beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('registerCtrl', {
             '$scope': $scope
         });
        };
	}));

	beforeEach(function(){
        createController();
    });



	describe('Register', function() {
		var userRegister;

		beforeEach(function(){
	        userRegister = {
				firstname: '',
			    lastname: '',
			    pseudo: '',
			    birthdate: '',
			    email: '',
	            password: '',
	            passwordConfirme: '',
	    		isFemale: true
			}
    	});

		describe('Variables', function() {
			

			it('variables should initialized', function() {
            	expect($scope.user).toEqual(userRegister);
        	});
		});
	});

});