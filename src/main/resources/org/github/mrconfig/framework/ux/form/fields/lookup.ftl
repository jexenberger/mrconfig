          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-7">
            <#include 'lookup_type.ftl'>
            <#if !field.readOnly>
              <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
              <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
            </#if>

          </div>
