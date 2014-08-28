          <div class="col-sm-offset-2 col-sm-7">
                <div class="checkbox">
                  <label>
                    <#include 'checkbox_type.ftl'>
                  </label>
                  <#if field.readOnly>
                    <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
                    <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
                  </#if>

                </div>
          </div>