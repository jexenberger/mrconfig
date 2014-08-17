          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
            <input id="${field.id}" popover="{{(${id}Form.${field.id}.$invalid) ? 'You have an error with this field' : ''}}" name="${field.id}Name" type="${field.type.id}" ng-model="model.${field.id}" class="form-control" readonly="${field.readonly}" <#include '../constraints.ftl'/>/>
          </div>