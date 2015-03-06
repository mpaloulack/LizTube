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

	var $scope, $rootScope, $location, authService, $q/*, $setValidity*/;

	beforeEach(inject(function (_$rootScope_, _$location_, _authService_, _$window_, _$q_) {
		$rootScope =_$rootScope_;
		$location = _$location_;
		authService = _authService_;
		$window= _$window_;
	    $q = _$q_;
	    //$setValidity = _$setValidity_;
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

    describe('variables', function(){

        it('variables should initialized', function() {
            expect($scope.errorRegister).toEqual('');
        });

    });



	describe('Register', function() {
		var registerPromise ;

		beforeEach(function(){
	        registerPromise = $q.defer();
            spyOn(authService, 'register').and.returnValue(registerPromise.promise);
    	});

    	beforeEach(function(){
            spyOn($rootScope, '$broadcast').and.callThrough();
            spyOn($location,'path').and.callThrough();
            $scope.register();
        });

    	it('should start global loading', function(){
            expect($rootScope.$broadcast).toHaveBeenCalledWith('loadingStatus', true);
        });

        it('should stop global loading when request is finished', function(){
            changePromiseResult(registerPromise, "failed");
            expect($rootScope.$broadcast).toHaveBeenCalledWith('loadingStatus', false);
        });


    	it('should return an error message', function(){
            changePromiseResult(registerPromise, "failed");
            expect($scope.errorRegister).toEqual("Error Register");
        });

		it('should be a successful register', function() {
			changePromiseResult(registerPromise, "resolve");
			expect($location.path).toHaveBeenCalledWith('/login');
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

    	/*it('should return email validation error', function(){
            changePromiseResult(emailVerifyPromise, "failed");
            expect($setValidity).toEqual(false);
        });*/

	});

	describe('pseudo validation', function() {
		
	});


});