describe('liztube.moastr', function(){

    var moast, $mdToast;

    beforeEach(module('liztube.moastr'));

    beforeEach(module(function($provide) {
        $mdToast= {
            show: function () {
                return true;
            }
        };
        $provide.constant('$mdToast', $mdToast);
    }));

    beforeEach(inject(function (_moastr_) {
        moast = _moastr_;
        spyOn($mdToast, 'show').and.callThrough();
    }));

    it('should toast an error', function(){
        moast.error("test");
        expect($mdToast.show).toHaveBeenCalledWith({
            template: '<md-toast class="moastr error"><span flex>test</span></md-toast>',
            hideDelay: 6000,
            position: 'left right bottom'
        });
    });

    it('should toast a warning', function(){
        moast.success("test");
        expect($mdToast.show).toHaveBeenCalledWith({
            template: '<md-toast class="moastr success"><span flex>test</span></md-toast>',
            hideDelay: 6000,
            position: 'left right bottom'
        });
    });

    it('should toast an info', function(){
        moast.info("test");
        expect($mdToast.show).toHaveBeenCalledWith({
            template: '<md-toast class="moastr info"><span flex>test</span></md-toast>',
            hideDelay: 6000,
            position: 'left right bottom'
        });
    });



});