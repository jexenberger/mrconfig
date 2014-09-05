<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
                    <input id="${field.id}"
                           name="${field.id}Name"
                           type="checkbox"
                           ng-model="model.${fieldId}"
                           <#include '../constraints.ftl'>/> ${field.label}
