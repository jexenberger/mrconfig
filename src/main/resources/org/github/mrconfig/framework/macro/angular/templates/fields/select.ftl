          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
              <select id="${field.id}" name="${field.id}Name"  ng-model="model.${field.id}" class="form-control" <#if field.required && capture>required</#if>>
                <#list field.options as option>
                  <option value="${option.car}">${option.cdr}</option>
                </#list>
              </select>
          </div>