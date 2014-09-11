<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
           <input id="${fieldId}"
                  name="${field.uuid}Name"
                  type="${field.htmlType()}"
                  ng-model="model.${fieldId}"
                  class="form-control"
                  readonly="true"/>