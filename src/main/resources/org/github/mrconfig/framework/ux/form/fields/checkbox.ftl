          <div class="col-sm-offset-2 col-sm-10">
                <div class="checkbox">
                  <label>
                    <input id="${field.id}" name="${field.id}Name" type="checkbox" ng-model="model.${field.id}" readonly="${field.readonly}" <#include '../constraints.ftl'>/> ${field.label}
                  </label>
                </div>
          </div>