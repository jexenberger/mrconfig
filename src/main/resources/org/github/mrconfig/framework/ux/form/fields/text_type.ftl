<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
            <input id="${field.id}"
                   placeholder="Enter ${field.label}"
                   name="${field.uuid}Name"
                   type="${field.type.id}"
                   ng-model="model.${fieldId}"
                   class="form-control"
                   <#include '../constraints.ftl'/> />