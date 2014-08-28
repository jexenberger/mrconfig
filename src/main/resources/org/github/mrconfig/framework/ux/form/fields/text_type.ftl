            <input id="${field.id}"
                   placeholder="Enter ${field.label}"
                   name="${field.id}Field"
                   type="${field.type.id}"
                   ng-model="model.${field.id}"
                   class="form-control"
                   <#include '../constraints.ftl'/> />