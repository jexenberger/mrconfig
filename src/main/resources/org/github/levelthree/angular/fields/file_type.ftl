<#include "index_builder.ftl">
            <input id="${field.uuid}Field"
                   name="${field.uuid}Name"
                   type="file"
                   ng-model="model.${fieldId}"
                   ng-readonly="!editable"
                   class="<#include 'input_class.ftl'/>"
                   <#if field.defaultValue??>
                   ng-init="model.${fieldId} = model.${fieldId} || '${field.defaultValue}'"
                   </#if>
                   <#include '../placeholders_tabs.ftl'/>
                   <#include '../constraints.ftl'/>
                   />
              <#include '../glyph_control.ftl'/>