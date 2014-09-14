<#include "index_builder.ftl">
            <input id="${field.uuid}Field"
                   placeholder="Enter ${field.label}"
                   name="${field.uuid}Name"
                   type="${field.type.id}"
                   ng-model="model.${fieldId}"
                   class="form-control input-sm"
                   <#include '../constraints.ftl'/> />