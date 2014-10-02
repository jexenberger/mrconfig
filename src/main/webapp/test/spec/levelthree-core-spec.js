describe("core", function() {

    var menuController;
    var base64;
    var modalController;
    var element;
    var compiled;
    var scope;
    var $httpBackend;
    var ltHATEOSUtils;

    beforeEach(function() {
        module('application');

        inject(function($rootScope, $controller, $injector, $compile) {
              scope         = $rootScope.$new();
              menuController = $controller("rs_menu_Controller", { $scope: scope });
              //modalController = $controller("reLookupController", { $scope: scope });
              base64 = $injector.get('LtBase64');
              ltHATEOSUtils = $injector.get("LtHATEOSUtils");
              alert(ltHATEOSUtils);
              scope.test = {
                id : 123,
                hello : 'world'
              }
              $httpBackend  = $injector.get('$httpBackend');
              $httpBackend.when('GET', '/test/123').respond(
                { href: '123', title: "Test 123" },{Link:'</test/123>; type="null"; rel="1"; title="all"'}
              );

              var html = "<form name='form'><input type='text' name='test' ng-model='test' lookup-valid='/test'/></form>";
              element = angular.element(html);
              compiled = $compile(element);
              compiled(scope);
              scope.$digest();


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
        $httpBackend.expectGET('/test/123');
        var form = scope.form;
        form.test.$setViewValue("123");
        scope.$digest();
        $httpBackend.flush();
        alert(JSON.stringify(scope.test));
        expect(form.test.$valid).toBe(true);

    });

    it("parseKeyValue ", function() {

        var simple = "key=value";
        var result = parseKeyValue(simple);
        expect(result.key).toBe('key');
        expect(result.value).toBe('value');

        var leading = "key='value'";
        result = parseKeyValue(leading, true);
        expect(result.key).toBe('key');
        expect(result.value).toBe('value');

        var leadingSpace = " key='value' ";
        result = parseKeyValue(leadingSpace, true);
        expect(result.key).toBe('key');
        expect(result.value).toBe('value');

    });


    it("parseHeaderLink", function() {
        var linkString = '<test/1>; type="null"; rel="1"; title="all" ';
        var link = ltHATEOSUtils.parseHeaderLink(linkString);
        expect(link.href).toBe('test/1');
        expect(link.type).toBe(null);
        expect(link.rel).toBe('1');
        expect(link.title).toBe('all');
    });


});