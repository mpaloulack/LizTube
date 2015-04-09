describe('liztube.header', function(){

    beforeEach(module('liztube.header'));
    beforeEach(module('liztube.userStatus'));
    beforeEach(module('liztube.moastr'));
    beforeEach(module('ngRoute'));
    beforeEach(module('liztube.upload.video'));
    beforeEach(module('angularFileUpload'));
    var createController, $scope, $rootScope, $mdSidenav, $http, $upload, constants, moastr;

    var mockConstants = {
        SERVER_ERROR : 'Une erreur inattendue est survenue. Si le problème persiste veuillez contacter l\'équipe de Liztube.',
        FILE_TYPE : 'Veuillez sélectionnez une vidéo de type "mp4"',
        FILE_SIZE: 'La taille de la vidéo ne dois pas dépasser 500 MO',
        NO_FILE_SELECTED: 'Aucune vidéo sélectionnée'
    };

    beforeEach(function() {
        module(function($provide) {
            $provide.constant('constants', mockConstants);
        });
    });

    beforeEach(inject(function (_$rootScope_,_$http_,_$upload_) {
        $rootScope =_$rootScope_;
        $http: _$http_;
        $upload: _$upload_;
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
        }
    };

    beforeEach(inject(function ($controller) {
        $scope = $rootScope.$new();
        createController = function () {
            return $controller('headerCtrl', {
                '$scope': $scope,
                '$http': $http,
                '$upload': $upload,
                'moastr': moastr,
                '$mdSidenav': $mdSidenav
            });
        };
    }));

    beforeEach(function(){
        createController();
    });

    describe('scope Init', function(){

        it('scope variables initialized', function(){
            expect($scope.isLoading).toEqual(false);
            expect($scope.showNotification).toEqual(false);
            expect($scope.uploadRate).toEqual(0);
            expect($scope.fileName).toEqual("");
            expect($scope.notification).toEqual(0);
        });

    });

    describe('loadingStatus when scope init to false', function(){

        beforeEach(function(){
            $scope.isLoading = false;
            $scope.uploadRate = 0;
        });

        it('should put isLoading to true if a loadingStatus event is broadcast with true', function(){
            $scope.$broadcast('loadingStatusForHeader', true);
            expect($scope.isLoading).toEqual(true);
            expect($scope.uploadRate).toEqual(0);
        });

    });

    describe('loadingStatus when scope init to true', function(){

        beforeEach(function(){
            $scope.isLoading = true;
        });

        it('should put isLoading to false if a loadingStatus event is broadcast with false', function(){
            $scope.$broadcast('loadingStatusForHeader', false);
            expect($scope.isLoading).toEqual(false);
            expect($scope.uploadRate).toEqual(0);
        });

    });

    /*describe('toggleRight method', function(){
        beforeEach(function(){
            spyOn($mdSidenav('right'), 'toggle').and.callThrough();
        });

        it('Should toggleRight open/close', function(){
            $scope.toggleRight();
            expect($mdSidenav('right').toggle).toHaveBeenCalled();
        });

    });*/

});
