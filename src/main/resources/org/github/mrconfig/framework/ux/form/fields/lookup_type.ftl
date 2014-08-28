            <input type="text"
                   ng-model="model.${field.id}"
                   typeahead="result as result.title for result in lookup('${field.lookup}', '${field.lookupFilter}', $viewValue)"
                   typeahead-loading="loading"
                   placeholder="Enter ${field.label}"
                   class="form-control"
                   <#include '../constraints.ftl'>/>
            <i ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
