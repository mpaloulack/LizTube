describe('liztube.dataService.authService', function(){
	var $httpBackend, authService, Restangular;

	beforeEach(module('liztube.dataService.authService'));

	beforeEach(inject(function(_$httpBackend_, _authService_, _Restangular_){
		$httpBackend = _$httpBackend_;
		Restangular = _Restangular_;
		authService = _authService_;
	}));
	
	afterEach(function() {
        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });

	it('should get current profil', function(){
		$httpBackend.expectGET('/currentProfil').respond();
		authService.currentUser();
        $httpBackend.flush();
	});
	
	it('should post for login', function(){
		$httpBackend.expectPOST('/login').respond();
		authService.login();
        $httpBackend.flush();
	});
});