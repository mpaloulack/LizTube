angular.module("liztube.upload.video",[
    "liztube.moastr",
    "ngRoute",
    "liztube.upload.video.page",
    'angularFileUpload' //https://github.com/danialfarid/angular-file-upload
]).controller("uploadVideoCtrl", function($scope, $http, $upload, constants, moastr, $mdSidenav, $location) {
    $scope.uploadRate = 0;
    $scope.id = 0;
    $scope.notifications = {
        "infos": []
    };
    /**
     * Catch upload video event to upload a video
     */

    $scope.$on('loadingUploadVideoForHeader', function(event, video) {
        $mdSidenav('right').toggle();
        $scope.id = $scope.id+1;
        $scope.$emit('addNotification', true);
        $scope.notifications.infos.push({
            id: $scope.id,
            fileName : constants.DOWNLOAD_ON_AIR_FILE_NAME + ": " + video.title,
            uploadRate : 0,
            percent : "0%",
            videoKey: ""
        });

        $upload.upload({
            url: '/api/video/upload',
            fields: {
                title: $scope.base64Encode(video.title),
                description: $scope.base64Encode(video.description),
                isPublic: video.isPublic,
                isPublicLink: video.isPublicLink
            },
            file: video.file
        }).progress(function (evt) {
            $scope.addVideoAsNotifications({
                id: $scope.id,
                fileName : constants.DOWNLOAD_ON_AIR_FILE_NAME + ": " + video.title,
                uploadRate : parseInt(99.0 * evt.loaded / evt.total),
                percent : parseInt(99.0 * evt.loaded / evt.total) + "%",
                videoKey: ""
            });
        }).success(function (data, status, headers, config) {
            $scope.addVideoAsNotifications({
                id: $scope.id,
                fileName : constants.UPLOAD_DONE + ": " + video.title,
                uploadRate : 100,
                percent : "100%",
                videoKey: data
            });
            moastr.successMin(constants.UPLOAD_DONE, 'top right');
        }).error(function (data, status, headers, config){
            moastr.error(constants.SERVER_ERROR, 'left right bottom');
        });
    });

    $scope.addVideoAsNotifications = function(video){
        for (var j = 0; j < $scope.notifications.infos.length; j++) {
            if (angular.equals($scope.notifications.infos[j].id,video.id)) {
                $scope.notifications.infos[j].fileName = video.fileName;
                $scope.notifications.infos[j].uploadRate = video.uploadRate;
                $scope.notifications.infos[j].percent = video.percent;
                $scope.notifications.infos[j].videoKey = video.videoKey;
            }
        }
    };

    /**
     * Close upload progress bar
     */
    $scope.hideProgressBar = function(index){
        $scope.notifications.infos.splice(index, 1);
        var notifications = {
            remove : true,
            delete : false
        };
        $scope.$emit('removeNotification', notifications);
    };

    $scope.$on('removeNotificationForSideBar', function(event, bool) {
        $scope.notifications = {
            "infos": []
        };
    });

    /**
     * Encode as base64
     * @param value
     * @returns {string}
     */
    $scope.base64Encode = function(value){
        var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;

        var input = UTF8Encode(value);

        while (i < input.length) {

            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);

            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;

            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }

            output = output + _keyStr.charAt(enc1) + _keyStr.charAt(enc2) + _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    };

    /**
     * Method required by base64Encode
     * @param string
     * @returns {string}
     * @constructor
     */
    function UTF8Encode(string){
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            }
            else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            }
            else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }
        }

        return utftext;
    }

}).directive('uploadVideo', function () {
    return {
        restrict: 'E',
        controller: 'uploadVideoCtrl',
        templateUrl: "uploadVideo.html",
        scope: {
            type: "@"
        }
    };
});