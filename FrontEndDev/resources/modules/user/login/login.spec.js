describe('liztube.login', function() {


	var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
	};
    
    beforeEach(module('liztube.login'));
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
            return $controller('loginCtrl', {
             '$scope': $scope
         });
        };
	}));

    beforeEach(function(){
        createController();
    });

    describe('variables', function(){

        it('variables should initialized', function() {
            expect($scope.errorLogin).toEqual('');
        });

    });

    describe('submit', function() {
        var loginPromise, currentProfilPromise, currentUserResponse;

        beforeEach(function(){
            loginPromise = $q.defer();
            spyOn(authService, 'login').and.returnValue(loginPromise.promise);
        });

        beforeEach(function(){
            currentProfilPromise = $q.defer();
            spyOn(authService, 'currentUser').and.returnValue(currentProfilPromise.promise);
            currentUserResponse = {
                pseudo:'max',
                roles:['ROLE_ADMIN']
            };
        });

        beforeEach(function(){
            spyOn($rootScope, '$broadcast').and.callThrough();
            spyOn($location,'path').and.callThrough();
            $scope.submit();
        });

        it('should start global loading', function(){
            expect($rootScope.$broadcast).toHaveBeenCalledWith('loadingStatus', true);
        });

        it('should stop global loading when request is finished', function(){
            changePromiseResult(loginPromise, "failed");
            expect($rootScope.$broadcast).toHaveBeenCalledWith('loadngStatus', false);
        });

        it('should return an error message', function(){
            changePromiseResult(loginPromise, "failed");
            expect($scope.errorLogin).toEqual("Error login");
        });

        it('should be a successful authentication', function(){
            changePromiseResult(loginPromise, "resolve");
            changePromiseResult(currentProfilPromise, "resolve", currentUserResponse);

            expect($window.user).toEqual(currentUserResponse);
            expect($rootScope.$broadcast).toHaveBeenCalledWith('userStatus', currentUserResponse);
            expect($location.path).toHaveBeenCalledWith('/');
        });
        
    });
	
});