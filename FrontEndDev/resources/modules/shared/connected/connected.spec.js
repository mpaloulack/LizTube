describe('liztube.userStatus',function(){

    var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
    };

    beforeEach(module('liztube.userStatus'));
    beforeEach(module('liztube.dataService.authService'));

    var createController, $scope, $rootScope, $location, authService, $window, $q, $mdSidenav;
    beforeEach(inject(function (_$rootScope_, _$location_, _authService_, _$window_, _$q_) {
        $rootScope =_$rootScope_;
        $location = _$location_;
        authService = _authService_;
        $window= _$window_;
        $q = _$q_;
    }));

    var moastr = {
        error: function(message){
            return message;
        }
    };

    var $mdSidenav = function(){
        return {
            close: function(){
                return true;
            }
        };
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('connectedCtrl', {
                '$scope': $scope,
                'moastr' : moastr,
                '$mdSidenav': $mdSidenav
            });
        };
    }));


    beforeEach(function(){
        createController();
    });

    describe('logOut', function(){
        var logoutPromise;
        beforeEach(function(){
            logoutPromise = $q.defer();
            spyOn(authService, 'logout').and.returnValue(logoutPromise.promise);
        });

        beforeEach(function(){

            $window.user = {
                "pseudo":"test",
                "roles":["test"]
            };
            $scope.checkUserConnected();

            spyOn($rootScope, '$broadcast').and.callThrough();
            spyOn($location, 'path').and.callThrough();
            spyOn(moastr, 'error').and.callThrough();
            $scope.logOut();
        });

        it('Should call the logout method', function(){
            expect(authService.logout).toHaveBeenCalled();
        });

        it('Should broadcast and redirect logout message if success', function(){
            changePromiseResult(logoutPromise, "resolve");
            expect($rootScope.$broadcast).toHaveBeenCalledWith('userStatus', undefined);
            expect($location.path).toHaveBeenCalledWith('/');
        });

        it('Should not broadcast logout message if error', function(){
            changePromiseResult(logoutPromise, "failed");
            expect($rootScope.$broadcast).not.toHaveBeenCalledWith('userStatus', undefined);
            expect(moastr.error).toHaveBeenCalledWith('An unexpected error occured. If the problem persists please contact the administrator.');
        });

    });

});
