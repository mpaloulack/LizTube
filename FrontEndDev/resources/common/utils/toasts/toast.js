angular.module("liztube.moastr",[])
    .factory("moastr", function($mdToast) {
    	return {
            error:error
        };

        function error(message){
            $mdToast.show({
                template: '<md-toast class="warn"><span flex>'+message+'</span></md-toast>',
                hideDelay: 6000,
                position: 'left right bottom'
            });
        }
    });
