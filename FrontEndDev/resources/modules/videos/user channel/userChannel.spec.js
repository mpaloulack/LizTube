describe('liztube.videos.channel', function(){

    var $scope, $rootScope, createController, $window;

    beforeEach(module("liztube.videos.channel"));
    beforeEach(module("ngRoute"));
    beforeEach(module("liztube.videos"));
    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('userChannelCtrl', {
                '$scope': $scope,
                'window': $window
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('scope Init', function(){

        /*beforeEach(function(){
            window.user = {
                "id":1,
                "email":"spywen@hotmail.fr",
                "pseudo":"spywen",
                "roles":["AUTHENTICATED","ADMIN"]
            };
            $scope.userId = window.user.id;
        });

        it('scope variables initialized', function(){
            expect($scope.pageTitle).toEqual("Mes vidéos");
            expect($scope.orderBy).toEqual("mostrecent");
            expect($scope.page).toEqual("");
            expect($scope.pagination).toEqual("");
            expect($scope.userId).toEqual(1);
            expect($scope.q).toEqual("");
            expect($scope.for).toEqual("user");
        });*/
    });
});