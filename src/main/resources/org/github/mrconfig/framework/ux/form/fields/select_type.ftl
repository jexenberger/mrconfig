<#include "index_builder.ftl">
              <select id="${field.uuid}Field"
                      name="${field.uuid}Name"
                      ng-model="model.${fieldId}"
                      placeholder="Enter ${field.label}"
                      class="form-control input-sm"
                      <#include '../constraints.ftl'>>
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>