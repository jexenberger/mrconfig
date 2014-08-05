application.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/views/servers', {
        templateUrl: '/views/servers/list.html',
        controller: 'ServersController'
      }).
      when('/views/servers/edit', {
        templateUrl: '/views/servers/edit.html',
        controller: 'ServersController'
      }).
      otherwise({
        redirectTo: '/views/servers'
      });
  }]);