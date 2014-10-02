//================== BEGIN SERVICES: ${module.name}
<#list module.resources?keys as resourceKey>
   <#assign resource=module.resources[resourceKey]>
   <#if resource.resourceUx??>

${resource.resourceUx.service}

   </#if>
</#list>
//================== END SERVICES: ${module.name}
