angular.module("liztube.toast",[])
    .controller("toastCtrl", function($scop, $mdToast) {
    	$scope.warnMessage= "erreur login";
    	$scope.closeToast = function(){
    		$mdToast.hide();
    	};
    });