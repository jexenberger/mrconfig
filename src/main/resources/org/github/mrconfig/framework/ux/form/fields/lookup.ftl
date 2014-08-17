          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
            <input type="text" ng-model="model.${field.id}" typeahead="result as result.title for result in lookup('${field.lookup}', '${field.lookupFilter}', $viewValue)" typeahead-loading="loading" class="form-control" readonly="${field.readonly}" <#include '../constraints.ftl'>/>
            <i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
          </div>
