//${editController} definition ---
${moduleName}Controllers.controller('${editController}',[
    '$scope',
    '$routeParams',
    '$window',
    '$http',
    '$location',
    '$injector',
    '$parse',
    '${serviceName}',
    function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {
        angular.extend(this, new LtEditController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));
    }]
);