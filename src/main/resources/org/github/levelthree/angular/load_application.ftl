var application = angular.module('application', [
    <#list modules?keys as moduleName>
    '${moduleName}',
    </#list>
    'ngRoute',
    'ngResource',
    'LtModule',
    'ui.bootstrap'
]);