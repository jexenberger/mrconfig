//${component.controllerName} definition ---
${component.module}Module.controller('${component.controllerName}',
      function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${component.service.name}, resource_${component.service.name}) {
          $scope.editable = true;
          angular.extend(this, new LtListController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${component.service.name}, resource_${component.service.name}));
      }
);