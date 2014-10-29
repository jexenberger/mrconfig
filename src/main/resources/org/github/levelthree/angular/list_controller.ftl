//${listController} definition ---
${module}Module.controller('${controllerName}',
      function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${service.name}, resource_${service.name}) {
          $scope.editable = true;
          angular.extend(this, new LtListController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${service.name}, resource_${service.name}));
      }
);