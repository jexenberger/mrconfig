//${listController} definition ---
${moduleName}Controllers.controller('${listController}',[
      '$scope',
      '$routeParams',
      '$window',
      '$http',
      '$location',
      '$injector',
      '$parse',
      '${serviceName}',
      function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {
          angular.extend(this, new LtListController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));
      }]);