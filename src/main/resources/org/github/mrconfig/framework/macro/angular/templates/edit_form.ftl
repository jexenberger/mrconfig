<#assign capture=true>
<div class="col-md-20" ng-controller="${id}Controller">
  <div class="panel panel-primary">
    <div class="panel-heading">${name}</div>
    <div class="panel-body">
                <div>
                <alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>
                </div>
        <form name="${id}Form" novalidate class="form-horizontal" role="form">
        <#list fields as field>
          <div class="form-group">
           <#switch field.type>
               <#case "Enum">
                 <#include "fields/select.ftl">
               <#break>
               <#case "Id">
                 <#include "fields/readonly.ftl">
               <#break>
               <#case "Hidden">
               <#break>
               <#case "Boolean">
                 <#include "fields/checkbox.ftl">
               <#break>
               <#case "Date">
                 <#include "fields/date.ftl">
               <#break>
               <#case "Lookup">
                 <#include "fields/lookup.ftl">
               <#break>
               <#default>
                 <#include "fields/text.ftl">
               <#break>
           </#switch>
           </div>
        </#list>
        </form>
        <div class="btn-group">
                <label class="btn btn-primary" ng-click="update(model);">Save</label>
                <label class="btn btn-primary" ng-click="reset();">Reset</label>
                <label class="btn btn-primary" ng-click="gotoList('${resourceName}/list/');">Done</label>
        </div>

    </div>
    </div>
  </div>
</div>
