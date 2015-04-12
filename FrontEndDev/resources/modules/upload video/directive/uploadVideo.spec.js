describe('liztube.upload.video', function(){

    beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video.page'));
    beforeEach(module('liztube.upload.video'));
    beforeEach(module('angularFileUpload'));
    var createController, $scope, $rootScope, $http, $upload, constants, moastr, $q;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        UPLOAD_DONE: "Téléchargement de la vidéo terminer",
        DOWNLOAD_ON_AIR_FILE_NAME: "Téléchargement de la vidéo : "
    };

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

    beforeEach(inject(function (_$rootScope_,_$http_, _$upload_, _$q_) {
        $rootScope =_$rootScope_;
        $http = _$http_;
        $upload = _$upload_;
        $q = _$q_;
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
                'constants': mockConstants,
                'moastr': moastr,
                '$mdSidenav': $mdSidenav
            });
        };
    }));

describe('uploadVideoCtrl', function(){

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

        describe('on loadingUploadVideoForHeader', function() {

            beforeEach(function(){
                $scope.videoLoading = false;
                spyOn(moastr, 'successMin').and.callThrough();
                spyOn(moastr, 'error').and.callThrough();
                spyOn($upload, 'upload').and.callThrough();
                var video = {
                    title: "test",
                    description: "test",
                    isPublic: false,
                    isPublicLink : false,
                    file: "test"
                };
                $scope.$broadcast('loadingUploadVideoForHeader', video);
            });

            it('should set videoLoading to true and create download file title', function () {
                expect($scope.videoLoading).toEqual(true);
                expect($scope.fileName).toEqual(mockConstants.DOWNLOAD_ON_AIR_FILE_NAME + "test");
            });

            it('$upload.upload should have been called', function () {
                expect($upload.upload).toHaveBeenCalled();
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

    });
});

