<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
                    <input id="${field.uuid}"
                           name="${field.uuid}Name"
                           type="checkbox"
                           ng-model="model.${fieldId}"
                           <#include '../constraints.ftl'>/> ${field.label}
