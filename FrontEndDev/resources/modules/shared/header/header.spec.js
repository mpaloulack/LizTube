describe('liztube.header', function(){

    beforeEach(module('liztube.header'));

    var createController, $scope,$rootScope,$mdSidenav;

    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

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

});
