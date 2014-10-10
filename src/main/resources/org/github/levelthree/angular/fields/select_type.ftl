<#include "index_builder.ftl">
              <select id="${field.uuid}Field"
                      name="${field.uuid}Name"
                      ng-model="model.${fieldId}"
                      class="<#include 'input_class.ftl'/>"
                      ng-readonly="!editable"
                      <#include '../placeholders_tabs.ftl'/>
                      <#include '../constraints.ftl'>>
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>