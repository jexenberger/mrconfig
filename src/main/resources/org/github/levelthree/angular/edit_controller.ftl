//${editController} definition ---
${moduleName}Module.controller('${editController}',
    function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${serviceName}, resource_${serviceName}) {
        $scope.editable = $routeParams.readonly == null;
        angular.extend(this, new LtEditController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${serviceName}, resource_${serviceName}));
    }
);