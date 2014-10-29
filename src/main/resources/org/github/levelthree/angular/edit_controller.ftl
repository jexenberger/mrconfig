//${component.controllerName} definition ---
${component.module}Module.controller('${component.controllerName}',
    function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${component.service.name}, resource_${component.service.name}) {
        $scope.editable = $routeParams.readonly == null;
        angular.extend(this, new LtEditController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${component.service.name}, resource_${component.service.name}));
        angular.extend(this, new LtModalController($scope, $routeParams, $window, $http, $location, $injector, $parse, resource_${component.service.name}));
    }
);