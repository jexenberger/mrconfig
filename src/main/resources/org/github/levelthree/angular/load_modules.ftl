<#list modules?keys as moduleName>
<#if (modules[moduleName].resources?size > 0)>
<#assign module=modules[moduleName]>

/*
 *=============================================================================
 * MODULE:  '${moduleName}'
 *=============================================================================
 */
var ${moduleName}Module =  angular.module('${moduleName}',[
    'ngRoute',
    'ngResource',
    'LtModule',
    'ui.bootstrap',
]);

<#include 'module_services.ftl'>

<#include 'module_controllers.ftl'>

<#include 'module_navigation.ftl'>

/*
 *=============================================================================
 * END MODULE:  '${moduleName}'
 *=============================================================================
 */
</#if>
</#list>
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
