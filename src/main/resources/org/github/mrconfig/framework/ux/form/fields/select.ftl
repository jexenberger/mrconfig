          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-7">
              <select id="${field.id}"
                      name="${field.id}Name"
                      ng-model="model.${field.id}"
                      placeholder="Enter ${field.label}"
                      class="form-control" <#include '../constraints.ftl'>>
                <#list field.defaultValueList as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>
              <#if !field.readOnly>
                  <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                  <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
              </#if>

          </div>