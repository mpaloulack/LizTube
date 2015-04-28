describe('liztube.videos', function(){

    var $scope, $rootScope, createController, videosService, constants, $q, $filter;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        NO_VIDEOS_FOUND : 'Aucune vidéos trouvées'
    };
    beforeEach(module('liztube.videos'));
    beforeEach(module('liztube.dataService.videosService'));
    beforeEach(module('liztube.moastr'));

    var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    var moastr = {
        error: function(message){
            return message;
        },
        successMin: function(message){
            return message;
        }
    };

    beforeEach(inject(function (_$rootScope_, _videosService_,_$q_, _$filter_) {
        $rootScope =_$rootScope_;
        videosService = _videosService_;
        $q = _$q_;
        $filter = _$filter_;
    }));

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('videosCtrl', {
                '$scope': $scope,
                'constants': mockConstants,
                'moastr': moastr
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('On watch params', function() {
        beforeEach(function(){
            $scope.pamaeters = {};
            $scope.params = {
                pageTitle: "Vidéos les plus récentes",
                orderBy: "mostrecent",
                page: "",
                pagination: "",
                user: "",
                q: "",
                for: "home"
            };

            //spyOn($scope,'getParams').and.callThrough();
        });

        it('Should params are setted and $scope.pamaeters created', function () {
            //expect($scope.getParams).toHaveBeenCalled();
            //expect($scope.pageTitle).toEqual("Vidéos les plus récentes");
        });
    });

    describe('filter function', function() {
        var filterPromise;
        beforeEach(function(){
            spyOn($scope,'filter').and.callThrough();
        });

        it('Should $scope.orderBy equal mostrecent if orderBy equal 1', function () {
            $scope.filter("1");
            expect($scope.orderBy).toEqual("mostrecent");
            expect($scope.pageTitle).toEqual("Vidéos les plus récentes");
        });

        it('Should $scope.orderBy equal mostviewed if orderBy equal 2', function () {
            $scope.filter("2");
            expect($scope.orderBy).toEqual("mostviewed");
            expect($scope.pageTitle).toEqual("Vidéos les plus vue");
        });

        it('Should $scope.orderBy equal mostshared if orderBy equal 3', function () {
            $scope.filter("3");
            expect($scope.orderBy).toEqual("mostshared");
            expect($scope.pageTitle).toEqual("Vidéos les plus partagées");
        });

        beforeEach(function(){
            $scope.videos = {};
            filterPromise = $q.defer();
            spyOn(videosService, 'getVideos').and.returnValue(filterPromise.promise);
        });

        /*it('should return an error message', function(){
            changePromiseResult(filterPromise, "failed");
            expect(moastr.error).toHaveBeenCalledWith(mockConstants.SERVER_ERROR,'left right bottom');
        });*/

        /*it('should be a successful update', function() {
            changePromiseResult(filterPromise, "resolve");
            expect($location.path).toHaveBeenCalledWith('/search');
        });*/
    });

    describe('filter formatTime', function() {
        it('should convert milliseconds to minutes and seconds', function () {
            expect($filter('formatTime')(1965605)).toEqual('32:45');
        });
        it('should convert milliseconds to minutes and seconds and hours', function () {
            expect($filter('formatTime')(1965604568)).toEqual('18:00:04');
        });
    });
});