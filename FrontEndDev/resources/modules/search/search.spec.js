describe('liztube.search', function(){

    var $scope, $rootScope, createController, location, route, $routeParams;

    beforeEach(module('liztube.search'));

    beforeEach(inject(function (_$location_, _$route_, _$rootScope_, _$routeParams_) {
        $rootScope =_$rootScope_;
        location = _$location_;
        route = _$route_;
        $routeParams = _$routeParams_;
    }));

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('searchCtrl', {
                '$scope': $scope
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('Search route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('search.html').respond(200);
            })
        );

        it('should load the search page on successful load', function() {
            location.path('/search/test');
            $rootScope.$digest();
            expect(route.current.controller).toBe('searchCtrl');
            expect(route.current.title).toBe('LizTube - Recherche');
            expect(route.current.page).toBe('Recherche');
        });
    });

    describe('scope Init', function(){
        it('scope variables initialized', function(){
            expect($scope.pageTitle).toEqual("Vidéos les plus récentes");
            expect($scope.orderBy).toEqual("mostrecent");
            expect($scope.page).toEqual("");
            expect($scope.pagination).toEqual("");
            expect($scope.userId).toEqual("");
            //expect($scope.q).toEqual("test");
            expect($scope.for).toEqual("home");
        });
    });
});