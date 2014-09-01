            <input type="text"
                   class="form-control"
                   placeholder="Enter ${field.label}"
                   datepicker-popup="{{format}}"
                   <#if field.parent?? && field.indexed>
                   ng-model="model.${field.parent}($index).${field.id}"
                   <#elseif field.parent?? && !field.indexed>
                   ng-model="model.${field.parent}.${field.id}"
                   <#else>
                   ng-model="model.${field.id}"
                   </#if>
                   is-open="${field.id?replace(".","_")}Open"
                   datepicker-options="dateOptions"
                   close-text="Close"
                   <#include '../constraints.ftl'>>

                   <span class="input-group-btn">
                        <button type="button" class="btn btn-default" ng-click="open('${field.id?replace(".","_")}Open',$event)"><i class="glyphicon glyphicon-calendar"></i></button>
                   </span>
            </input>
