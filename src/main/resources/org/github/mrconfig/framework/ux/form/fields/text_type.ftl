<#include "index_builder.ftl">
            <input id="${field.uuid}Field"
                   name="${field.uuid}Name"
                   type="${field.type.id}"
                   ng-model="model.${fieldId}"
                   class="form-control input-sm"
                   <#if field.defaultValue??>
                   ng-init="model.${fieldId} = model.${fieldId} || '${field.defaultValue}'"
                   </#if>
                   <#include '../placeholders_tabs.ftl'/>
                   <#include '../constraints.ftl'/>/>
              <#include '../glyph_control.ftl'/>