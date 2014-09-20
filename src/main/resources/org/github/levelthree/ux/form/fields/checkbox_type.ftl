<#include "index_builder.ftl">
                    <input class="input-sm"
                           id="${field.uuid}Field"
                           name="${field.uuid}Name"
                           type="checkbox"
                           ng-readonly="!editable"
                           ng-model="model.${fieldId}"
                           <#if (field.tabIndex > -1)>
                           tabindex="${field.tabIndex}"
                           </#if>
                           <#include '../constraints.ftl'>>
                           <h5>${field.label}</h5></input>

