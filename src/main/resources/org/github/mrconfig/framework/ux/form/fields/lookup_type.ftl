<#include "index_builder.ftl">
                    <input id="${field.uuid}Field"
                           class="form-control"
                           type="text"
                           name="${field.uuid}Name"
                           ng-model="model.${fieldId}"
                           placeholder="Click to lookup value for ${field.label}"
                           lookup-valid="${field.lookup}"
                           <#include '../constraints.ftl'>>
                            <span class="input-group-btn btn-group-sm">
                               <button type="button" class="btn btn-default btn-group-sm" ng-click="openLookup('${field.lookup}','${field.lookupFilter}','${field.label}')"><i class="glyphicon glyphicon-search" ></i></button>
                            </span>
                            <button ng-show="loadingLocations" class="glyphicon glyphicon-refresh"></i>
                    </input>

