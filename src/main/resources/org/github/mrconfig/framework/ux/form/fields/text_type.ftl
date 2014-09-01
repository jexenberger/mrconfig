            <input id="${field.id}"
                   placeholder="Enter ${field.label}"
                   name="${field.id}Field"
                   type="${field.type.id}"
                   <#if field.parent?? && field.indexed>
                   ng-model="model.${field.parent}($index).${field.id}"
                   <#elseif field.parent?? && !field.indexed>
                   ng-model="model.${field.parent}.${field.id}"
                   <#else>
                   ng-model="model.${field.id}"
                   </#if>
                   class="form-control"
                   <#include '../constraints.ftl'/> />