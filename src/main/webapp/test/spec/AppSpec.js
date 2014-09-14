describe("App", function() {

    var menuController;
    var base64;
    var modalController;
    var element;
    var compiled;
    var scope;
    var $httpBackend;

    beforeEach(function() {
        module('application');

        inject(function($rootScope, $controller, $injector, $compile) {
              scope         = $rootScope.$new();
              menuController = $controller("rs_menu_Controller", { $scope: scope });
              //modalController = $controller("reLookupController", { $scope: scope });
              base64 = $injector.get('base64');

              scope.test = {
                id : 543,
                hello : 'world'
              }
              var html = "<form name='form'><input type='text' name='test' ng-model='test' lookup-valid='/test'/></form>";
              element = angular.element(html);
              compiled = $compile(element);
              compiled(scope);
              scope.$digest();

              $httpBackend  = $injector.get('$httpBackend');
              $httpBackend.when('GET', '/test/123').respond(
                { 'id': 123, 'name': "Test 123" }
              );

        });


    });



    it("App : toLink", function() {
        var link = {
            href : "test"
        }
        var result = isLink(link);
        expect(result).toBe(true);


    });

    it("base64:Service encode/decode", function() {
        var encoded = base64.encode("hello world");
        expect(encoded).toBe('aGVsbG8gd29ybGQ=');
        var unencoded = base64.decode(encoded);
        expect(unencoded).toBe('hello world');

    });

    it("lookupValue:Directive set/get", function() {
        var form = scope.form;
        form.test.$setViewValue("123");
        scope.$digest();
        expect(form.test.$valid).toBe(true);
    });

});