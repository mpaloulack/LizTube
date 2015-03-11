describe('liztube.loading', function(){

    beforeEach(module('liztube.loading'));

    var createController, $scope, $rootScope;

    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('loadingCtrl', {
                '$scope': $scope
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
            $rootScope.$broadcast('loadingStatus', true);
            expect($scope.isLoading).toEqual(true);
        });

    });

    describe('loadingStatus when scope init to true', function(){

        beforeEach(function(){
            $scope.isLoading = true;
        });

        it('should put isLoading to false if a loadingStatus event is broadcast with false', function(){
            $rootScope.$broadcast('loadingStatus', false);
            expect($scope.isLoading).toEqual(false);
        });

    });

});
