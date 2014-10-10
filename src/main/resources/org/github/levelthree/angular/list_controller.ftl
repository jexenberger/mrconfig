//${listController} definition ---
${moduleName}Module.controller('${listController}',
      function($scope, $routeParams, $window, $http, $location, $injector, $parse, ${serviceName}, resource_${serviceName}) {
          $scope.editable = true;
          angular.extend(this, new LtListController($scope, $routeParams, $window, $http, $location, $injector, $parse, ${serviceName}, resource_${serviceName}));
      }
);