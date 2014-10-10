<#if field.parent?? && field.indexed>
     <#assign fieldId = field.parent + "[$index]." + field.id>
     <#assign idx = "$index">
<#elseif field.parent?? && !field.indexed>
     <#assign fieldId = field.parent + field.id>
     <#assign idx = "-1">
<#else>
     <#assign fieldId = field.id>
     <#assign idx = "-1">
</#if>
         <div class="input-group col-xs-10">
            <input id="${field.uuid}Field"
                   type="text"
                   class="<#include 'input_class.ftl'/>"
                   name="${field.uuid}Name"
                   ng-readonly="!editable"
                   datepicker-popup="{{format}}"
                   ng-model="model.${fieldId}"
                <#if field.indexed>
                   is-open="state['${field.uuid}'][${idx}].open"
                <#else>
                   is-open="state['${field.uuid}'].open"
                </#if>
                   datepicker-options="dateOptions"
                   close-text="Close"
                   <#include '../placeholders_tabs.ftl'/>
                   <#include '../constraints.ftl'>/>
                   <span class="input-group-btn btn-group-sm">
                        <button type="button" class="btn btn-info btn-group-sm" ng-click="open('${field.uuid}',$event, ${idx})"><i class="glyphicon glyphicon-calendar"></i></button>
                   </span>
         </div>