<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
     <#assign idx = "$index">
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
     <#assign idx = "-1">
<#else>
     <#assign fieldId = field.id>
     <#assign idx = "-1">
</#if>
            <input id="${fieldId}"
                   class="form-control input-sm"
                   type="date"
                   placeholder="Enter ${field.label}"
                   name="${fieldId}Name"
                   ng-model="model.${fieldId}"
                   <#include '../constraints.ftl'>>
            </input>
