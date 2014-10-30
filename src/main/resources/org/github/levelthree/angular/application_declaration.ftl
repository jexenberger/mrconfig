var application = angular.module('application', [
    <#list modules?keys as moduleName>
    <#if (modules[moduleName].resources?size > 0)>
    '${moduleName}',
    </#if>
    </#list>
    'ngRoute',
    'ngResource',
    'LtModule',
    'ui.bootstrap'

]);