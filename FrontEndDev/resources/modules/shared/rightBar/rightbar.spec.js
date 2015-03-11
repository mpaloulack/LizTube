describe('liztube.rightBar', function(){

    beforeEach(module('liztube.rightBar'));
    //beforeEach(module('liztube.userStatus'));

    var createController, $scope, $rootScope, $mdSidenav;

    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

    var $mdSidenav = function(test){
        return {
            close: function(){
                return true;
            }
        };
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('RightSideBarCtrl', {
                '$scope': $scope,
                '$mdSidenav': $mdSidenav
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    /*describe('Close method', function(){

        beforeEach(function(){
            spyOn($mdSidenav('right'),'close').and.callThrough();
            $scope.closeRightBar();
        });

        it('Should close right bar', function(){
            expect($mdSidenav('right').close()).toHaveBeenCalled();
        });

    });*/

});
