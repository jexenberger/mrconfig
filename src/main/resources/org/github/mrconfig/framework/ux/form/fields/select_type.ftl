<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
<#else>
     <#assign fieldId = field.id>
</#if>
              <select id="${field.uuid}"
                      name="${field.uuid}Name"
                      ng-model="model.${fieldId}"
                      placeholder="Enter ${field.label}"
                      class="form-control input-sm"
                      <#include '../constraints.ftl'>>
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>