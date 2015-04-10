describe('liztube.upload.video', function(){

    beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video.page'));
    var createController, $scope, $rootScope, constants, moastr;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        FILE_TYPE_ERROR : 'Veuillez sélectionnez une vidéo de type "mp4"',
        FILE_SIZE_ERROR: 'La taille de la vidéo ne dois pas dépasser 500 MO',
        NO_FILE_SELECTED: 'Aucune vidéo sélectionnée',
        FILE_SIZE_ALLOWED: 524288000
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_) {
        $rootScope =_$rootScope_;
    }));

    var moastr = {
        error: function(message){
            return message;
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('FileUploadController', {
                '$scope': $scope,
                'constants': constants,
                'moastr': moastr
            });
        };
    }));

    describe('FileUploadController', function(){

        beforeEach(function(){
            createController();
        });

        describe('scope Init', function(){

            it('scope variables initialized', function(){

            });

        });

        describe('submit', function(){
            beforeEach(function(){
                isPublic = false;
                isPublicLink = false;
                $scope.video = {
                    files: "",
                    title: "test",
                    description: "test",
                    confidentiality: '0'
                };
                var files = $scope.video.files;
                spyOn($scope, '$emit').and.callThrough();
                spyOn(moastr, 'error').and.callThrough();
                $scope.submit();
            });

            /*it('Should set isPublic & isPublicLink to false if video.confidentiality equal 0', function(){
                var files = null;
                expect(moastr.error).toHaveBeenCalled(mockConstants.NO_FILE_SELECTED);
            });

            it('Should set isPublic & isPublicLink to false if video.confidentiality equal 0', function(){
                expect(isPublic).toEqual(false);
                expect(isPublicLink).toEqual(false);
            });

            it('Should set isPublic & isPublicLink to true if video.confidentiality equal 1', function(){
                $scope.video.confidentiality = '1';
                expect(isPublic).toEqual(true);
                expect(isPublicLink).toEqual(true);
            });

            it('Should set isPublic to false & isPublicLink to true if video.confidentiality equal 2', function(){
                $scope.video.confidentiality = '2';
                expect(isPublic).toEqual(false);
                expect(isPublicLink).toEqual(true);
            });

            it('Should emit for loadingUploadVideo', function(){
                expect($scope.$emit).toHaveBeenCalledWith('loadingUploadVideo', undefined);
            });

            it('Should emit for addNotification', function(){
                expect($scope.$emit).toHaveBeenCalledWith('addNotification', true);
            });*/

        });
    });
});

