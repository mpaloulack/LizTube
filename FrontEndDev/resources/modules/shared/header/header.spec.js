describe('liztube.header', function(){

    beforeEach(module('liztube.header'));
    beforeEach(module('liztube.userStatus'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video'));
    var createController, $scope, $rootScope, $mdSidenav;


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
                '$mdSidenav': $mdSidenav
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
        });

    });

    describe('addNotification when scope init to boolean', function(){

        beforeEach(function(){
            $scope.showNotification = false;
            $scope.notification = 0;
        });

        it('should put showNotification to boolean if a addNotification event is broadcast with boolean', function(){
            $scope.$broadcast('addNotificationForHeader', true);
            expect($scope.showNotification).toEqual(true);
            expect($scope.notification).toEqual(1);
        });

    });

    describe('removeNotificationForHeader when scope init to boolean', function(){

        beforeEach(function(){
            $scope.showNotification = true;
            $scope.notification = 1;
        });

        it('should put notification to -1 if a removeNotificationForHeader event is broadcast with boolean', function(){
            $scope.$broadcast('removeNotificationForHeader', true);
            expect($scope.notification).toEqual(0);
            expect($scope.showNotification).toEqual(false);
        });

    });

    describe('loadingStatus when scope init to false', function(){

        beforeEach(function(){
            $scope.isLoading = false;
        });

        it('should put isLoading to true if a loadingStatus event is broadcast with true', function(){
            $scope.$broadcast('loadingStatusForHeader', true);
            expect($scope.isLoading).toEqual(true);
        });

    });

    describe('loadingStatus when scope init to true', function(){

        beforeEach(function(){
            $scope.isLoading = true;
        });

        it('should put isLoading to false if a loadingStatus event is broadcast with false', function(){
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
