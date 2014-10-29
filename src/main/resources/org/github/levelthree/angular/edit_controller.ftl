//${editController} definition ---
${module}Module.controller('${controllerName}',
    function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${service.name}, resource_${service.name}) {
        $scope.editable = $routeParams.readonly == null;
        angular.extend(this, new LtEditController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${service.name}, resource_${service.name}));
        angular.extend(this, new LtModalController($scope, $routeParams, $window, $http, $location, $injector, $parse, resource_${service.name}));
    }
);