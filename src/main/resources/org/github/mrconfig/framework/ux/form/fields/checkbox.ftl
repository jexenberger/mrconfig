          <div class="col-sm-offset-2 col-sm-7">
                <div class="checkbox">
                  <label>
                    <input id="${field.id}" name="${field.id}Name" type="checkbox" ng-model="model.${field.id}"  <#include '../constraints.ftl'>/> ${field.label}
                  </label>
                  <#if field.readOnly>
                    <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                    <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                  </#if>

                </div>
          </div>