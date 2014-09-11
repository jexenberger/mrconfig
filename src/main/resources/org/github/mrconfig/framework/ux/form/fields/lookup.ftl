          <label for="${field.uuid}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-7">
            <#include 'lookup_type.ftl'>
            <#if !field.readOnly>
              <span ng-show="${id}Form.${field.uuid}.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
              <span ng-show="${id}Form.${field.uuid}.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
            </#if>

          </div>
