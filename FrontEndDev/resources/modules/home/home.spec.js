describe('liztube.home', function(){

    var $scope, $rootScope, createController, location, route;

    beforeEach(module('liztube.home'));

    beforeEach(inject(function (_$location_, _$route_, _$rootScope_) {
        $rootScope =_$rootScope_;
        location = _$location_;
        route = _$route_;
    }));

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('homeCtrl', {
                '$scope': $scope
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('Home route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('home.html')
                    .respond(200);
            }));

        it('should load the home page on successful load of /', function() {
            location.path('/');
            $rootScope.$digest();
            expect(route.current.controller).toBe('homeCtrl');
            expect(route.current.title).toBe('LizTube - Accueil');
            expect(route.current.page).toBe('Home');
        });
    });

    describe('404 route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('404.html')
                    .respond(200);
            }));

        it('should load the 404 page on successful load of /404', function() {
            location.path('/404');
            $rootScope.$digest();
            expect(route.current.title).toBe('LizTube - 404');
            expect(route.current.page).toBe('404 : Page non trouvé');
        });
    });

});