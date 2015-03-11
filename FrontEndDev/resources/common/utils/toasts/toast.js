angular.module("liztube.moastr",[])
    .factory("moastr", function($mdToast) {
    	return {
            error:error,
            success:success,
            info:info
        };

        function error(message){
            $mdToast.show({
                template: '<md-toast class="moastr error"><span flex>'+message+'</span></md-toast>',
                hideDelay: 6000,
                position: 'left right bottom'
            });
        }
        function success(message){
            $mdToast.show({
                template: '<md-toast class="moastr success"><span flex>'+message+'</span></md-toast>',
                hideDelay: 6000,
                position: 'left right bottom'
            });
        }
        function info(message){
            $mdToast.show({
                template: '<md-toast class="moastr info"><span flex>'+message+'</span></md-toast>',
                hideDelay: 6000,
                position: 'left right bottom'
            });
        }
    });
