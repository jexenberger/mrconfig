<#list module.resources?keys as resourceKey>
   <#assign resource=module.resources[resourceKey]>
   <#if resource.resourceUx??>
         $routeProvider
             <#if resource.resourceUx.createComponent?? && resource.resourceUx.editComponent??>

             </#if>
             <#if resource.resourceUx.viewComponent??>
                .when('${resource.resourceUx.viewLink}', {
                    templateUrl : '${resource.resourceUx.viewTemplate}',
                    controller  : '${resource.resourceUx.viewController}',
                    resolve : ${resource.resourceUx.viewRouteResolve}
                })
             </#if>
             <#if resource.resourceUx.editComponent??>
                .when('${resource.resourceUx.editLink}', {
                    templateUrl : '${resource.resourceUx.editTemplate}',
                    controller  : '${resource.resourceUx.editController}',
                    resolve : ${resource.resourceUx.editRouteResolve}
                })
             </#if>
             <#if resource.resourceUx.createComponent??>
                .when('${resource.resourceUx.createLink}', {
                    templateUrl : '${resource.resourceUx.createTemplate}',
                    controller  : '${resource.resourceUx.createController}',
                    resolve : ${resource.resourceUx.createRouteResolve}
                })
             </#if>
   </#if>
</#list>
}]);
