<#list forms as form>

createService(services,'${form.id}Service','${form.resourceName}');
createGenericController(controllers, '${form.id}Controller', '${form.id}Service', '${form.resourceName}');


</#list>