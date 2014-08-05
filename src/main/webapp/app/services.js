var useAjax = false;
var myConfigurerServices = angular.module('mrConfigurerServices', ['ngResource']);

myConfigurerServices.factory('Environment', ['$resource',
  function($resource){
    return $resource('data/environments.json', {}, {
      query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
    });
  }]);

