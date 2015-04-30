describe('liztube.video.watch', function() {
    var changePromiseResult = function (promise, status, value) {
        if (status === 'resolve')
            promise.resolve(value);
        else
            promise.reject(value);
        $rootScope.$digest();
    };

    beforeEach(module('liztube.video.watch'));
    beforeEach(module('liztube.dataService.videosService'));
    beforeEach(module('ngRoute'));
    beforeEach(module('ngSanitize'));
    beforeEach(module('com.2fdevs.videogular'));
    beforeEach(module('com.2fdevs.videogular.plugins.controls'));
    beforeEach(module('com.2fdevs.videogular.plugins.overlayplay'));
    beforeEach(module('com.2fdevs.videogular.plugins.poster'));


    var $scope, $rootScope, $location, videosService, $q, constants, $window, createController, keyVideo, $sce;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.'
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
        keyVideo = "d6e8cff8-2ba1-40da-b39f-038b6ff0f926";
    });

    beforeEach(inject(function (_$rootScope_, _$location_, _$route_, _videosService_, _$window_, _$q_, _$sce_) {
        $rootScope =_$rootScope_;
        $location = _$location_;
        route = _$route_;
        videosService = _videosService_;
        $window= _$window_;
        $q = _$q_;
        $sce = _$sce_;
    }));

    var moastr = {
        error: function(message){
            return message;
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('watchCtrl', {
                '$scope': $scope,
                'moastr' : moastr
            });
        };
    }));

    describe('Watch route', function() {
        beforeEach(inject(
            function($httpBackend) {
                $httpBackend.expectGET('watch.html')
                    .respond(200);
            }));

        /*it('should load the video watch page on successful load of /watch=videoKey', function() {
            $location.path('/watch/'+keyVideo);
            $rootScope.$digest();
            expect(route.current.controller).toBe('watchCtrl');
            expect(route.current.title).toBe('LizTube - ');
            expect(route.current.page).toBe('Watch');
            expect(route.current.params.videoKey).toBe(keyVideo);
        });*/
    });

    beforeEach(function(){
        createController();
    });

    describe('Get watch Info', function() {
        var videoDescPromise, videoDesc, videoConfig;

        beforeEach(function(){
            spyOn(videosService, 'getVideoData').and.returnValue(videoDescPromise.promise);
            spyOn($location,'path').and.callThrough();
            spyOn(moastr, 'error').and.callThrough();
            $scope.getVideoDesc();
        });

        beforeEach(function(){
            videoDesc={
                title: "Titre",
                description: "blabla",
                views: 100,
                key: keyVideo,
                ownerPseudo: "pseudo",
                ownerEmail: "pseudo@liztube.fr",
                ownerId: 1,
                isPublic: true,
                isPublicLink: false,
                creationDate: 1429800731,
                duration: ""
            };
            videoConfig = {
                sources: [
                    {src: $sce.trustAsResourceUrl("/api/video/watch/"+keyVideo), type: "video/mp4"}
                ],
                theme: "/app/dist/libs/videogular-themes-default/videogular.css",
                plugins: {
                    poster: "/api/video/thumbnail/"+keyVideo+"?width=1280&height=720"
                }
            };
            $scope.videoDesc=null;
            videoDescPromise = $q.defer();
        });




       /* it('should return an error message', function(){
            changePromiseResult(videoDescPromise, "failed");
            expect(moastr.error).toHaveBeenCalledWith(mockConstants.SERVER_ERROR, 'left right bottom');
        });

        it('should be a successful get user info', function() {
            changePromiseResult(videoDescPromise, "resolve", videoDesc);
            expect($scope.videoDesc).toEqual(videoDesc);
            expect($scope.videoDesc.creationDate).toEqual(new Date(2015, 3, 23));
            expect($scope.config).toEqual(videoConfig);
        });*/

    });

});