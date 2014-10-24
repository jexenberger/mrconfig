${module.name}Module.config(['$routeProvider','$injector',function($routeProvider, $injector) {
<#list module.resources?keys as resourceKey>
   <#assign resource=module.resources[resourceKey]>
   <#if resource.resourceUx??>
         $routeProvider
             <#if resource.resourceUx.createComponent??>
                .when('${resource.resourceUx.listLink}', {
                    templateUrl : '${resource.resourceUx.listTemplate}',
                    resolve : ${resource.resourceUx.listRouteResolve}
                })
             </#if>
             <#if resource.resourceUx.viewComponent??>
                .when('${resource.resourceUx.viewLink}', {
                    templateUrl : '${resource.resourceUx.viewTemplate}',
                    resolve : ${resource.resourceUx.viewRouteResolve}
                })
             </#if>
             <#if resource.resourceUx.editComponent??>
                .when('${resource.resourceUx.editLink}', {
                    templateUrl : '${resource.resourceUx.editTemplate}',
                    resolve : ${resource.resourceUx.editRouteResolve}
                })
             </#if>
             <#if resource.resourceUx.createComponent??>
                .when('${resource.resourceUx.createLink}', {
                    templateUrl : '${resource.resourceUx.createTemplate}',
                    resolve : ${resource.resourceUx.createRouteResolve}
                })
             </#if>
   </#if>
</#list>
}]);

