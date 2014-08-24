<#list resources as resource>

createService(services,'${resource.name}Service','${resource.path}');
createGenericController(controllers, '${resource.name}Controller', '${resource.name}Service', '${resource.path}','${resource.name}Form' );


</#list>