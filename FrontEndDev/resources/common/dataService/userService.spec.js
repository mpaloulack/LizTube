describe('liztube.dataService.userService', function(){
    var $httpBackend, userService, Restangular;

    beforeEach(module('liztube.dataService.userService'));

    beforeEach(inject(function(_$httpBackend_, _userService_, _Restangular_){
        $httpBackend = _$httpBackend_;
        Restangular = _Restangular_;
        userService = _userService_;
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });

    it('should get current info profil', function(){
        $httpBackend.expectGET('/user/infoProfile').respond();
        userService.userProfil();
        $httpBackend.flush();
    });

    it('should post for register', function(){
        $httpBackend.expectPOST('/user/infoProfile').respond();
        userService.updateProfil({});
        $httpBackend.flush();
    });
});