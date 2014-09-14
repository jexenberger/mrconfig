<#include "index_builder.ftl">
                    <input class="input-sm"
                           id="${field.uuid}Field"
                           name="${field.uuid}Name"
                           type="checkbox"
                           ng-model="model.${fieldId}"
                           <#include '../constraints.ftl'>>
                           <h5>${field.label}</h5></input>

