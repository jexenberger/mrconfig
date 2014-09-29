<#list modules?keys as moduleName>
<#if (modules[moduleName].resources?size > 0)>
<#assign module=modules[moduleName]/>
//----- navigation for ${moduleName}
<#include 'module_navigation.ftl'/>
</#if>
</#list>