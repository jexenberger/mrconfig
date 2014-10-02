<#list modules?keys as moduleName>
<#if (modules[moduleName].resources?size > 0)>
<#assign module=modules[moduleName]>

/*
 *=============================================================================
 * MODULE:  '${moduleName}'
 *=============================================================================
 */
${moduleName}Module =  application.module('application.${moduleName}');
${moduleName}Controllers = ${moduleName}Module.module('application.${moduleName}.controllers',[]);
${moduleName}Services = ${moduleName}Module.module('application.${moduleName}.services',[]);

<#include 'module_services.ftl'>

<#include 'module_controllers.ftl'>
/*
 *=============================================================================
 * END MODULE:  '${moduleName}'
 *=============================================================================
 */
</#if>
</#list>
