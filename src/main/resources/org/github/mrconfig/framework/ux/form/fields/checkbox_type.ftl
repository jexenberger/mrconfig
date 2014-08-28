                    <input id="${field.id}"
                           name="${field.id}Name"
                           type="checkbox"
                           ng-model="model.${field.id}"
                           <#include '../constraints.ftl'>/> ${field.label}
