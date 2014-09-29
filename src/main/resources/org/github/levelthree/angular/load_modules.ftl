<#list modules?keys as moduleName>
<#if (modules[moduleName].resources?size > 0)>
// Declaration for ${moduleName}
${moduleName}Module =  application.module('application.${moduleName}');
${moduleName}Controllers = ${moduleName}Module.module('application.${moduleName}.controllers',[]);
${moduleName}Services = ${moduleName}Module.module('application.${moduleName}.services',[]);

</#if>
</#list>