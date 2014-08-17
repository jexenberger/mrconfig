          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
              <select id="${field.id}" name="${field.id}Name"  ng-model="model.${field.id}" class="form-control" <#include '../constraints.ftl'> readonly="${field.readonly}">
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>
          </div>