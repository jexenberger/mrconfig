
<#assign capture=true>
<div class="panel-heading clearfix" ng-controller="${id}Controller">
     <h2 class="panel-title pull-left" style="padding-top: 7.5px;">${name}</h2>
     <div class="btn-group pull-right">
                             <button type="button"
                                     class="glyphicon glyphicon-cog btn btn-danger dropdown-toggle pull-right"
                                     data-toggle="dropdown">
                                 Actions <span class="caret"></span>
                             </button>
                             <ul class="dropdown-menu" role="menu">
                                 <li ng-repeat="link in model.links">
                                   <a ng-href="javascript:applyAction(link.href, link.rel)">{{link.title}}</a>
                                 </li>
                             </ul>
                         </div>
</div>
    <div class="panel-body">
                <div>
                <alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>
                </div>
        <form name="${id}Form" novalidate class="form-horizontal" role="form">
          <#list byGroups?keys as group>
            <#assign fields=byGroups[group]>
            <fieldset>
            <#if group != 'default'>
            <legend>${group}</legend>
            </#if>
            <#list fields as field>
                <div class="form-group has-feedback" ng-class="validationClass(${id}Form.${field.id}Field);">
                 <#include "fields/"+field.type.templatePath>
                 <#if !field.readOnly>
                 <span ng-show="${id}Form.${field.id}Field.$error.required" class="help-block has-warning">Field is required</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.max" class="help-block has-warning">Field is greater than allowed value</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.min" class="help-block has-warning">Field is less than allowed value</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.minlength" class="help-block has-warning">Field is less than allowed length</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.maxlength" class="help-block has-warning">Field is greater than allowed length</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.number" class="help-block has-warning">Field should be numeric</span>
                 <span ng-show="${id}Form.${field.id}Field.$error.pattern" class="help-block has-warning">Field contains invalid characters</span>
                 </#if>
                </div>
            </#list>
            </fieldset>
           </#list>
           <#if collectionForms??>
           <#list collectionForms?keys as key>
               <#assign collectionForm=collectionForms[key]>
                             <div class="form-group">
                             <legend>${collectionForm.name}</legend>
                              <table class="table table-striped" ng-if="searchPage != null">
                                  <thead>
                                      <tr>
                                        <th>test</th>
                                      </tr>
                                  </thead>
                                  <tbody>
                                      <tr>
                                         <td>hello</td>
                                      </tr>
                                  </tbody>
                              </table>
                              </div>
           </#list>
           </#if>
        </form>
        <div class="btn-group">
                <label class="btn btn-primary" ng-disabled="${id}Form.$invalid" ng-click="update(model);">Save</label>
                <label class="btn btn-primary" ng-click="reset();">Reset</label>
                <label class="btn btn-primary" ng-click="gotoList();">Done</label>
        </div>
  </div>
</div>
