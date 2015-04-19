describe('liztube.updatepassword', function() {

    var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
    };

    beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.updatepassword'));
    beforeEach(module('liztube.dataService.userService'));

    var $scope, $rootScope, $location, userService, $q, constants, $window, createController;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.'
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
            return $controller('updatePasswordCtrl', {
                '$scope': $scope,
                'constants': mockConstants,
                'moastr' : moastr
            });
        };
    }));



    describe('UpdatePassword route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('updatepassword.html')
                    .respond(200);
            }));

        it('should load the updatepassord page on successful load of /updatepassword', function() {
            $location.path('/updatepassword');
            $rootScope.$digest();
            expect(route.current.controller).toBe('updatePasswordCtrl');
            expect(route.current.title).toBe('LizTube - Mise à jour du mot de passe');
            expect(route.current.page).toBe('Mise à jour du mot de passe');
        });
    });

    describe('UpdatePasswordCtrl', function() {
        beforeEach(function(){
            createController();
        });

        describe('scope Init', function(){

            it('scope variables initialized', function(){
                expect($scope.password.oldPassword).toEqual("");
                expect($scope.password.newPassword).toEqual("");
                expect($scope.verify.password).toEqual("");
                expect($scope.errorUpdate).toEqual("");
            });

        });

        describe('update', function() {
            var submitPromise;

            beforeEach(function(){
                submitPromise = $q.defer();
                spyOn(userService, 'updatePassword').and.returnValue(submitPromise.promise);

                $scope.update();
            });

            beforeEach(function(){
               $scope.password={
                   oldPassword: "oldPassword",
                   newPassword: "newPassword"
               };
            });

            beforeEach(function(){
                spyOn($location,'path').and.callThrough();
                spyOn(moastr, 'error').and.callThrough();
                userService.updatePassword($scope.password);
            });

            it('should return an error message', function(){
                changePromiseResult(submitPromise, "failed");
                expect(moastr.error).toHaveBeenCalledWith(mockConstants.SERVER_ERROR,'left right bottom');
            });

            it('should be a successful update', function() {
                changePromiseResult(submitPromise, "resolve");
                expect($location.path).toHaveBeenCalledWith('/profil');
            });
        });
    });

});