          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
           <input id="${field.id}" name="${field.id}Name" type="${field.htmlType()}" ng-model="model.${field.id}" class="form-control" readonly="true"/>
          </div>