<#include "index_builder.ftl">
           <input id="${field.uuid}Field"
                  class="<#include 'input_class.ftl'/>"
                  name="${field.uuid}Name"
                  type="${field.type.id}"
                  ng-model="model.${fieldId}"
                  readonly="true"/>