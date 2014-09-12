<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
            <input id="${field.uuid}"
                   class="form-control input-sm"
                   type="text"
                   name="${field.uuid}Name"
                   ng-model="model.${fieldId}"
                   typeahead="result as result.title for result in lookup('${field.lookup}', '${field.lookupFilter}', $viewValue)"
                   typeahead-loading="loading"
                   placeholder="Enter ${field.label}"
                   <#include '../constraints.ftl'>/>
            <i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
