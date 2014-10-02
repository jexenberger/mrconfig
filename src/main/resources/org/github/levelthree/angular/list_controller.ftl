${name}Controllers.controller('${resource.resourceUx.controllerName}',[
      '$scope',
      '$routeParams',
      '$window',
      '$http',
      '$location',
      '$injector',
      '$parse',
      '${resource.name}Service',
      function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {
          angular.extend(this, new LtListController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));
      }]
  );