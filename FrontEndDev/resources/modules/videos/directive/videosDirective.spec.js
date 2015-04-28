describe('liztube.videos', function(){

    var $scope, $rootScope, createController, videosService, constants, $q;

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

    beforeEach(inject(function (_$rootScope_, _videosService_,_$q_) {
        $rootScope =_$rootScope_;
        videosService = _videosService_;
        $q = _$q_;
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

    /*describe('On watch params', function() {
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
            expect($scope.pageTitle).toEqual("Vidéos les plus récentes");
        });
    });*/
});