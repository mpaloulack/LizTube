/**
 * Created by Youcef on 05/03/2015.
 */
angular.module("liztube.date",[

]).directive('dateToTimestamp', function() {
    return {
        require: 'ngModel',
        link: function(scope, ele, attr, ngModel) {
            // view to model
            ngModel.$parsers.push(function(value) {
                return Date.parse(value);
            });
        }
    };
});