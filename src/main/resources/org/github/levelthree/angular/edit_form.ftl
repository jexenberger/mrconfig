<#include 'field_include_macro.ftl'/>
<#assign capture=true>
<div  ng-controller="${controllerName}" ng-cloak>
  <div class="container-fluid col-xs-12 col-sm-12 col-md-12">
     <div class="container-fluid">
        <legend><h4>${form.name}<small ng-if="!editable"> - template</small> <small ng-if="editable && isNew"> - create</small><small ng-if="editable && !isNew"> - edit</small></h4></legend>
     </div>
     <div class="container-fluid">
         <div class="btn-group pull-right btn-group-sm">
                                 <button type="button"
                                         class="btn btn-danger dropdown-toggle pull-right"
                                         data-toggle="dropdown">
                                     <i class="glyphicon glyphicon-cog">&nbsp;</i>Actions <span class="caret"></span>
                                 </button>
                                 <ul class="dropdown-menu" role="menu">
                                     <li ng-repeat="link in model.links">
                                       <a ng-href="javascript:applyAction(link.href, link.rel)">{{link.title}}</a>
                                     </li>
                                 </ul>
         </div>
     </div>
     <br/>
     <div class="container-fluid">
         <div>
                <alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>
         </div>
        <form name="${form.id}Form" class="form-horizontal" novalidate role="form">
          <#list form.byGroups?keys as group>
            <#assign fields=form.byGroups[group]>
            <#if group != 'default'>
            <legend><h5>${form.group}</h5></legend>
            <br/>
            </#if>
            <#list fields as field>
                 <#include "fields/"+field.type.templatePath+".ftl">
            </#list>
           </#list>


           <#if collectionForms??>
           <fieldset>
           <#list collectionForms?keys as key>
               <#assign collectionForm=collectionForms[key]>
                             <#assign fieldSize = 12 / (collectionForm.fields?size + 1)?int>
                             <legend><h5><b>${form.collectionForm.name}</b></h5></legend>
                              <table class="table table-hover">
                                  <thead>
                                      <tr>
                                        <th class="col-md-1">Delete</th>
                                      <#list collectionForm.fields as field>
                                        <th class="col-md-${fieldSize}">${field.label}</th>
                                      </#list>
                                      </tr>
                                  </thead>
                                  <tbody>
                                      <tr ng-repeat="${key}Item in model.${key}">
                                         <ng-form name="${key}Form">
                                            <td class="col-md-1">
                                              <div class="has-feedback form-group-sm">
                                                <div class="btn-group btn-group-sm">
                                                    <button class="btn btn-danger" ng-click="removeCollectionItem('${key}',$index);"><i class="glyphicon glyphicon-remove">&nbsp;</i>Delete</button></td>
                                                </div>
                                              </div>
                                            <#list collectionForm.fields as field>
                                            <td class="col-md-${fieldSize}">
                                               <div class="has-feedback form-group-sm">
                                                <#include "fields/"+field.type.templatePath+"_type.ftl">
                                               </div>
                                            </td>
                                            </#list>
                                         </ng-form>
                                      </tr>
                                  </tbody>
                              </table>

           <div class="btn-group btn-group-sm">
                  <label class="btn btn-primary"  ng-click="addToCollection('${key}');"><i class="glyphicon glyphicon-plus">&nbsp;</i>Add</label>
           </div>
           </#list>

           </fieldset>
           </#if>
        </form>
        <hr/>
        <div class="btn-group btn-group-sm pull-right">
                <button type="button" class="btn btn-success" ng-disabled="${form.id}Form.$invalid || !editable" ng-click="update(model, '${form.id}Form');"><i class="glyphicon glyphicon-ok">&nbsp;</i>Save</button>
                <button type="button" class="btn btn-primary" ng-click="reset();" ng-disabled="!editable"><i class="glyphicon glyphicon-refresh">&nbsp;</i>Reset</button>
                <button type="button" class="btn btn-primary" ng-click="gotoList();"><i class="glyphicon glyphicon-off">&nbsp;</i>Done</button>
        </div>
     </div>
  </div>
</div>
