angular.module("liztube.videos",[
    "liztube.dataService.videosService",
    "liztube.moastr"
]).controller("videosCtrl", function($scope, constants, videosService, moastr, $window) {
    $scope.pamaeters = {};
    $scope.flexSize = 20;

    $scope.getWindowSize = function (size) {
        if(size <= 500){
            $scope.flexSize = 100;
        }else if(size >= 500 && size <= 800){
            $scope.flexSize = 50;
        }else if(size >= 800 && size <= 1300){
            $scope.flexSize = 33;
        }else{
            $scope.flexSize = 20;
        }

    };

    $scope.getWindowSize($window.innerWidth);
    $scope.$watch("params",function(){

        if(!_.isUndefined($scope.getParams().for) && $scope.getParams().for === "user"){
            $scope.showConfidentiality = true;
        }else{
            $scope.showConfidentiality = false;
        }

        if(!_.isUndefined($scope.getParams().for) && $scope.getParams().for === "home"){
            $scope.showSelectVideos = true;
        }else{
            $scope.showSelectVideos = false;
        }

        if(!_.isUndefined($scope.getParams().pageTitle) && $scope.getParams().pageTitle !== ""){
            $scope.pageTitle = $scope.getParams().pageTitle;
        }else{
            $scope.pageTitle = "Liztube vidéos";
        }

        if(!_.isUndefined($scope.getParams().orderBy) && $scope.getParams().orderBy !== ""){
            $scope.orderBy = $scope.getParams().orderBy;
        }else{
            $scope.orderBy = "q";
        }
        if(!_.isUndefined($scope.getParams().page) && $scope.getParams().page !== ""){
            $scope.pamaeters.page = $scope.getParams().page;
        }
        if(!_.isUndefined($scope.getParams().pagination) && $scope.getParams().pagination !== ""){
            $scope.pamaeters.pagination = $scope.getParams().pagination;
        }
        if(!_.isUndefined($scope.getParams().user) && $scope.getParams().user !== ""){
            $scope.pamaeters.user = $scope.getParams().user;
        }
        if(!_.isUndefined($scope.getParams().q) && $scope.getParams().q !== ""){
            $scope.pamaeters.q = $window.encodeURIComponent($scope.getParams().q);
        }

        videosService.getVideos($scope.orderBy, $scope.pamaeters).then(function(data){
            if(data.length === 0 && (_.isUndefined($scope.getParams().q) || $scope.getParams().q === "")){
                $scope.noVideoFound = constants.NO_VIDEOS_FOUND;
            }else if(data.length === 0 && (!_.isUndefined($scope.getParams().q) || $scope.getParams().q !== "")) {
                $scope.noVideoFound = constants.NO_VIDEOS_FOUND + " pour la recherche '" + $window.decodeURIComponent($scope.pamaeters.q) + "'";
            }else{
                $scope.videos = data;
                console.log("videosTotalCount : " + data.videosTotalCount);
                console.log("currentPage : " + data.currentPage);
                console.log("totalPage : " + data.totalPage);
            }
        },function(){
            moastr.error(constants.SERVER_ERROR,'left right bottom');
        }).finally(function(){
            //finally
        });
    });

    $scope.filter = function(orderBy){
        if(orderBy === "1"){
            $scope.orderBy = "mostrecent";
            $scope.pageTitle = "Vidéos les plus récentes";
        }else if(orderBy === "2"){
            $scope.orderBy = "mostviewed";
            $scope.pageTitle = "Vidéos les plus vue";
        }else if(orderBy === "3"){
            $scope.orderBy = "mostshared";
            $scope.pageTitle = "Vidéos les plus partagées";
        }
        $scope.videos = {};
        videosService.getVideos($scope.orderBy, $scope.pamaeters).then(function(data){
            if(data.length === 0){
                $scope.noVideoFound = constants.NO_VIDEOS_FOUND;
            }else{
                $scope.noVideoFound = "";
                $scope.videos = data;
            }
        },function(){
            moastr.error(constants.SERVER_ERROR,'left right bottom');
        }).finally(function(){
            //finally
        });
    };

}).directive('liztubeVideos', function ($window) {
    return {
        restrict: 'E',
        controller: 'videosCtrl',
        templateUrl: "videosDirective.html",
        replace: true,
        scope: {
            pageTitle:"@",
            orderBy: "@",
            page: "@",
            pagination: "@",
            user: "@",
            q: "@",
            for: "@"
        },
        link: function(scope, element, attrs) {
            scope.params = {
                pageTitle: scope.pageTitle,
                orderBy: scope.orderBy,
                page: scope.page,
                pagination: scope.pagination,
                user: scope.user,
                q: scope.q,
                for: scope.for
            };
            scope.getParams = function(){
                return scope.params;
            };

            $window.addEventListener('resize', function(){
                scope.getWindowSize(element[0].offsetWidth);
                scope.$apply();
            });
        }
    };
}).filter('formatTime', function() {
    return function(milliseconds) {
        var seconds = parseInt((milliseconds/1000)%60);
        var minutes = parseInt((milliseconds/(1000*60))%60);
        var hours = parseInt((milliseconds/(1000*60*60))%24);
        var out = "";

        hours = (hours < 10 && hours > 0) ? "0" + hours : hours;
        minutes = (minutes < 10) ? "0" + minutes : minutes;
        seconds = (seconds < 10) ? "0" + seconds : seconds;

        if(hours === 0){
            out = minutes + ":" + seconds;
        }else{
            out = hours + ":" + minutes + ":" + seconds;
        }


        return out;
    };
});
