//================== BEGIN CONTROLLERS: ${module.name}
<#list module.resources?keys as resourceKey>
   <#assign resource=module.resources[resourceKey]>
   <#if resource.resourceUx??>

${resource.resourceUx.controllerViews}

   </#if>
</#list>
//================== END CONTROLLERS ${module.name}
