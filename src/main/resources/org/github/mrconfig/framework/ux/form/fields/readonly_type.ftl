<#include "index_builder.ftl">
           <input id="${field.uuid}Field"
                  class="form-control input-sm"
                  name="${field.uuid}Name"
                  type="${field.htmlType()}"
                  ng-model="model.${fieldId}"
                  readonly="true"/>