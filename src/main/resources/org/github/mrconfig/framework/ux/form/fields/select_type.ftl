              <select id="${field.id}"
                      name="${field.id}Name"
                      ng-model="model.${field.id}"
                      placeholder="Enter ${field.label}"
                      class="form-control"
                      <#include '../constraints.ftl'>>
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>