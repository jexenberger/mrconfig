
<#assign capture=true>
<div  ng-controller="${id}Controller">
  <div class="container-fluid col-xs-12 col-sm-12 col-md-12">
     <div class="container-fluid">
        <legend><h4>${name}<small ng-if="isNew"> - create</small><small ng-if="!isNew"> - edit</small></h4></legend>
     </div>
     <div class="container-fluid">
         <div class="btn-group pull-right btn-group-sm">
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
     <br/>
     <div class="container-fluid">
         <div>
                <alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">{{alert.msg}}</alert>
         </div>
        <form name="${id}Form" novalidate role="form">
          <#list byGroups?keys as group>
            <#assign fields=byGroups[group]>
            <fieldset>
            <#if group != 'default'>
            <legend><h5>${group}</h5></legend>
            </#if>
            <#assign counter=0>
            <#assign didRun=false>
            <#list fields as field>
                <#assign counter=counter+1>
                <#assign didRun=true>
                <#if counter==1>
                <div class="row">
                </#if>
                <div class="col-xs-6 col-sm-6 col-md-6" ng-class="validationClass(${id}Form.${field.uuid}Name);">
                 <#include "fields/"+field.type.templatePath+".ftl">
                </div>
                <#if field.readOnly>
                <#assign counter=2>
                </#if>
                <#if counter==2>
                </div>
                <#assign counter=0>
                </#if>

            </#list>
            <#if counter<2 && didRun>
                </div>
            </#if>
            </fieldset>
            <br/>
           </#list>


           <#if collectionForms??>
           <fieldset>
           <#list collectionForms?keys as key>
               <#assign collectionForm=collectionForms[key]>
                             <#assign fieldSize = 12 / (collectionForm.fields?size + 1)?int>
                             <legend><h5><b>${collectionForm.name}</b></h5></legend>
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
                                                    <button class="btn btn-danger glyphicon glyphicon-remove" ng-click="removeCollectionItem('${key}',$index);">&nbsp;Delete</button></td>
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
                  <label class="btn btn-primary glyphicon glyphicon-plus"  ng-click="addToCollection('${key}');">&nbsp;Add</label>
           </div>
           </#list>

           </fieldset>
           </#if>
        </form>
        <br/>
        <hr/>
        <div class="btn-group btn-group-sm">
                <button type="button" class="btn btn-success glyphicon glyphicon-ok " ng-disabled="${id}Form.$invalid" ng-click="update(model);">&nbsp;Save</button>
                <button type="button" class="btn btn-primary glyphicon glyphicon-log-in" ng-click="reset();">&nbsp;Reset</button>
                <button type="button" class="btn btn-primary glyphicon glyphicon-off" ng-click="gotoList();">&nbsp;Done</button>
        </div>
     </div>
  </div>
</div>
