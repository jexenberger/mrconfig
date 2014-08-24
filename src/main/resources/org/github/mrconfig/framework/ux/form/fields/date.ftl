          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-7">
            <div class="input-group">
            <input type="text"
                   class="form-control"
                   placeholder="Enter ${field.label}"
                   datepicker-popup="{{format}}"
                   ng-model="model.${field.id}"
                   is-open="opened"
                   datepicker-options="dateOptions"
                   close-text="Close" <#include '../constraints.ftl'>/>

                        <span class="input-group-btn">
                            <button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>
            </div>
            <#if !field.readOnly>
              <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
              <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
            </#if>

          </div>