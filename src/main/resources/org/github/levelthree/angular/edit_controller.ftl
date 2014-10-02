${moduleName}Controllers.controller('${resource.resourceUx.controllerName}',[
    '$scope',
    '$routeParams',
    '$window',
    '$http',
    '$location',
    '$injector',
    '$parse',
    '${resource.serviceName}',
    function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {
        angular.extend(this, new LtEditController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));
    }]
);