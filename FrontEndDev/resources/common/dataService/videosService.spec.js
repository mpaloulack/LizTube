describe('liztube.dataService.videosService', function(){
    var $httpBackend, videosService, Restangular;

    beforeEach(module('liztube.dataService.videosService'));

    beforeEach(inject(function(_$httpBackend_, _videosService_, _Restangular_){
        $httpBackend = _$httpBackend_;
        Restangular = _Restangular_;
        videosService = _videosService_;
    }));

    afterEach(function() {
        $httpBackend.verifyNoOutstandingRequest();
        $httpBackend.verifyNoOutstandingExpectation();
    });

    it('should get videos by user', function(){
        $httpBackend.expectGET('/video/search/q?user=1').respond();
        videosService.getVideos("q",{user: 1});
        $httpBackend.flush();
    });

    it('should accept more than one params', function(){
        $httpBackend.expectGET('/video/search/q?page=10&user=1').respond();
        videosService.getVideos("q",{page: 10, user: 1});
        $httpBackend.flush();
    });

    it('should get video thumbnail by key', function(){
        $httpBackend.expectGET('/video/thumbnail/3ef39e77-8c2c-4afb-8ad7-d96958536c0f').respond();
        videosService.getVideoThumbnail("3ef39e77-8c2c-4afb-8ad7-d96958536c0f");
        $httpBackend.flush();
    });


    /*it('should get video thumbnail by videoKey and size', function(){
        $httpBackend.expectGET('/video/thumbnail/3ef39e77-8c2c-4afb-8ad7-d96958536c0f?width=100&height=100').respond();
        videosService.getVideoThumbnail("3ef39e77-8c2c-4afb-8ad7-d96958536c0f",{width: 100, height:100});
        $httpBackend.flush();
    });*/

    it('should get video watch by key', function(){
        $httpBackend.expectGET('/video/watch/3ef39e77-8c2c-4afb-8ad7-d96958536c0f').respond();
        videosService.getVideoWatch("3ef39e77-8c2c-4afb-8ad7-d96958536c0f");
        $httpBackend.flush();
    });


    it('should get video data by key', function(){
        $httpBackend.expectGET('/video/3ef39e77-8c2c-4afb-8ad7-d96958536c0f').respond();
        videosService.getVideoData("3ef39e77-8c2c-4afb-8ad7-d96958536c0f");
        $httpBackend.flush();
    });

});
