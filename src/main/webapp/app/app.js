

/*
var myApplication = angular.module('myApplication', [
    'ngRoute',
    'myApplicationControllers',
    'ui.bootstrap'
]);
*/
var application = angular.module('application', [
    'ngRoute',
    'ngResource',
    'services',
    'controllers',
    'ui.bootstrap'
]);

var services = angular.module('services',[]);
var controllers = angular.module('controllers', []);

isLink = function(val) {
 if (val == null) {
    return false;
 }
 if (val.hasOwnProperty("href")) {
    return true;
 }

}

application.factory('myInterceptor', ['$log', function($log) {

    var myInterceptor = {
        // optional method
              'request': function(config) {
                // do something on success
                 $log.debug(JSON.stringify(config));
                return config;
              },
    };

    return myInterceptor;
}]);

application.config(['$httpProvider', function($httpProvider) {

    $httpProvider.interceptors.push('myInterceptor');
}]);

controllers.controller('rs_menu_Controller',['$scope','$rootScope','$http', '$location',  function($scope, $rootScope, $http,$location) {

    if($rootScope.menu == null) {

        $http.get("/menus").success(function(data) {
            var groups = [];
            for (var group in data.menuGroups) {
              var menuItem = {};
              menuItem.name = group
              menuItem.items = data.menuGroups[group];
              groups.push(menuItem);
            }
            $rootScope.menu = groups;
        });
    }


}]);






