          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-7">
            <input type="text"
                   ng-model="model.${field.id}"
                   typeahead="result as result.title for result in lookup('${field.lookup}', '${field.lookupFilter}', $viewValue)"
                   typeahead-loading="loading"
                   placeholder="Enter ${field.label}"
                   class="form-control"  <#include '../constraints.ftl'>/>
            <i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>

            <#if !field.readOnly>
              <span ng-show="${id}Form.${field.id}Field.$valid" class="glyphicon glyphicon-ok form-control-feedback"></span>
              <span ng-show="${id}Form.${field.id}Field.$invalid" class="glyphicon glyphicon-remove form-control-feedback"></span>
            </#if>

          </div>
