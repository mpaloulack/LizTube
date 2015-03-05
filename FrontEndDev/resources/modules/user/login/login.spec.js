describe('liztube.login', function() {


	var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
	};

    
    beforeEach(module('liztube.login'));
    beforeEach(module('liztube.dataservice.authService'));
    beforeEach(module('ngMessages'));

    var $scope, $rootScope, $location, authService, $window, $interval;

    beforeEach(inject(function (_$rootScope_, _$location_, _authService_, _$window_, _$interval_) {
    	$rootScope =_$rootScope_;
    	$location = _$location_;
    	authService = _authService_;
    	$window= _$window_;
    	$interval = _$interval_;
    }));

	beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('loginCtrl', {
             '$scope': $scope
         });
        };
	}));


	it('should behave...', function() {
		expect(true).toEqual(true);
	});
	
});