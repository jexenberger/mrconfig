<#list modules?keys as moduleName>
<#if (modules[moduleName].resources?size > 0)>
<#assign module=modules[moduleName]>

/*
 *=============================================================================
 * MODULE:  '${moduleName}'
 *=============================================================================
 */
var ${moduleName}Module =  application.module('${moduleName}',[]);
var ${moduleName}Controllers = ${moduleName}Module.module('controllers',[]);
var ${moduleName}Services = ${moduleName}Module.module('services',[]);

<#include 'module_services.ftl'>

<#include 'module_controllers.ftl'>
/*
 *=============================================================================
 * END MODULE:  '${moduleName}'
 *=============================================================================
 */
</#if>
</#list>
