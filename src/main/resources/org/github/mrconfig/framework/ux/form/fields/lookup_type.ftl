<#include "index_builder.ftl">
                  <div class="input-group">
                    <input id="${field.uuid}Field"
                           class="form-control input-sm"
                           type="text"
                           name="${field.uuid}Name"
                           ng-model="model.${fieldId}"
                           lookup-valid="${field.lookup}"
                           <#include '../placeholders_tabs.ftl'/>
                           <#include '../constraints.ftl'>/>
                    <span class="input-group-btn btn-group-sm">
                         <button type="button" class="btn btn-info" type="button" ng-click="openLookup('${field.lookup}','${field.lookupFilter}','${field.label}','model.${fieldId}', '${field.uuid}DisableHelp')"><i class="glyphicon glyphicon-search" ></i></button>
                    </span>
                  </div>

