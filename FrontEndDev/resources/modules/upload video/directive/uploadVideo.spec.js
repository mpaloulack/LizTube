describe('liztube.upload.video', function(){

    beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video.page'));
    beforeEach(module('liztube.upload.video'));
    beforeEach(module('angularFileUpload'));
    var createController, $scope, $rootScope, $http, $upload, constants, moastr;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        UPLOAD_DONE: "Téléchargement de la vidéo terminer"
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_,_$http_) {
        $rootScope =_$rootScope_;
        $http = _$http_;
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
        },
        successMin: function(message){
            return message;
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('uploadVideoCtrl', {
                '$scope': $scope,
                '$http': $http,
                '$upload': $upload,
                'constants': constants,
                'moastr': moastr,
                '$mdSidenav': $mdSidenav
            });
        };
    }));

/*describe('uploadVideoCtrl', function(){

        beforeEach(function(){
            createController();
        });

        describe('scope Init', function(){

            it('scope variables initialized', function(){
                expect($scope.videoLoading).toEqual(false);
                expect($scope.uploadRate).toEqual(0);
                expect($scope.fileName).toEqual("");
            });

        });
        describe('loadingUploadVideoForHeader', function() {
            beforeEach(function(){
                spyOn(moastr, 'successMin').and.callThrough();
                spyOn(moastr, 'error').and.callThrough();
                spyOn($upload, 'upload').and.callThrough();
                var video = {
                    title: "test",
                    description: "test",
                    isPublic: false,
                    isPublicLink : false,
                    file: "test"
                }
                $scope.$broadcast('loadingUploadVideoForHeader', video);
            });

            it('Should $broadcast for loadingUploadVideoForHeader', function () {
                expect($scope.videoLoading).toEqual(false);
                expect($scope.fileName).toEqual("Téléchargement de la vidéo : test");
            });

            it('Should $upload.upload called', function () {
                var upload = {
                    url: '/api/video/upload',
                    fields: {
                        title: "test",
                        description: "test",
                        isPublic: false,
                        isPublicLink: false
                    },
                    file: "test"
                }
                expect($upload.upload).toHaveBeenCalledWith(upload);
            });
        });

        describe('closeProgressBar', function(){
            beforeEach(function(){
                spyOn($scope, '$emit').and.callThrough();
                $scope.videoLoding = true;
                $scope.closeProgressBar();
            });

            it('Should emit for removeNotification', function(){
                expect($scope.$emit).toHaveBeenCalledWith('removeNotification', true);
            });


            it('Should set videoLoading to true', function(){
                expect($scope.videoLoading).toEqual(false);
            });

        });

    });*/
});

