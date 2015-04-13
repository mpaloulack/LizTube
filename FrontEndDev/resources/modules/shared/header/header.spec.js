describe('liztube.header', function(){

    beforeEach(module('liztube.header'));
    beforeEach(module('liztube.userStatus'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video'));
    var createController, $scope, $rootScope, $mdSidenav, constants;

    var mockConstants = {
        NO_NOTIFICATIONS_FOUND: "Vous n'avez aucune notifications"
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

    var $mdSidenav = function(test){
        return {
            toggle: function(){
                return true;
            }
        };
    };

    var moastr = {
        error: function(message){
            return message;
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('headerCtrl', {
                '$scope': $scope,
                '$mdSidenav': $mdSidenav,
                'constants': mockConstants
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('scope Init', function(){

        it('scope variables initialized', function(){
            expect($scope.isLoading).toEqual(false);
            expect($scope.showNotification).toEqual(false);
            expect($scope.notification).toEqual(0);
            expect($scope.noNotification).toEqual(mockConstants.NO_NOTIFICATIONS_FOUND);
        });

    });

    describe('on addNotification', function(){

        beforeEach(function(){
            $scope.showNotification = false;
            $scope.notification = 0;
        });

        it('should put showNotification to boolean if a addNotification event is broadcast and add 1 to the notification count', function(){
            $scope.$broadcast('addNotificationForHeader', true);
            expect($scope.showNotification).toEqual(true);
            expect($scope.notification).toEqual(1);
        });

    });

    describe('on removeNotificationForHeader', function(){

        beforeEach(function(){
            $scope.showNotification = true;
        });

        it('should soustract 1 to notification count', function(){
            $scope.notification = 1;
            $scope.$broadcast('removeNotificationForHeader', true);
            expect($scope.notification).toEqual(0);
            expect($scope.noNotification).toEqual(mockConstants.NO_NOTIFICATIONS_FOUND);
        });

        it('should keep showNotification to true if some notification are left', function(){
            $scope.notification = 2;
            $scope.$broadcast('removeNotificationForHeader', true);
            expect($scope.showNotification).toEqual(true);
        });

        it('should set showNotification to false if no notification are left', function(){
            $scope.notification = 1;
            $scope.$broadcast('removeNotificationForHeader', true);
            expect($scope.showNotification).toEqual(false);
        });

        it('should do nothing if no notification are left', function(){
            $scope.notification = 0;
            $scope.showNotification = false;
            $scope.$broadcast('removeNotificationForHeader', true);
            expect($scope.showNotification).toEqual(false);
            expect($scope.notification).toEqual(0);
            expect($scope.noNotification).toEqual(mockConstants.NO_NOTIFICATIONS_FOUND);
        });

    });

    describe('set loading status', function(){

        it('should put isLoading to true if a loadingStatus event is broadcast with true', function(){
            $scope.isLoading = false;
            $scope.$broadcast('loadingStatusForHeader', true);
            expect($scope.isLoading).toEqual(true);
        });

        it('should put isLoading to false if a loadingStatus event is broadcast with false', function(){
            $scope.isLoading = true;
            $scope.$broadcast('loadingStatusForHeader', false);
            expect($scope.isLoading).toEqual(false);
        });

    });


    /*describe('toggleRight method', function(){
        beforeEach(function(){
            spyOn($mdSidenav('right'), 'toggle').and.callThrough();
        });

        it('Should toggleRight open/close', function(){
            $scope.toggleRight();
            expect($mdSidenav('right').toggle).toHaveBeenCalled();
        });

    });*/

});
