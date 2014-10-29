<#include 'field_include_macro.ftl'/>
<#assign capture=false>
<div ng-controller="${uxContext['list']}" ng-cloak>
   <#assign fieldSize = 9 / (searchFields?size)?int>
  <div class="container-fluid col-xs-12 col-sm-12 col-md-12">
      <div class="container-fluid">
      <legend><h4>${name}<small> - search</small></h4></legend>
      <form novalidate role="form">
         <#assign counter=0>
         <#assign didRun=false>
         <#list searchFields as field>
             <#assign counter=counter+1>
             <#assign didRun=true>
             <#if counter==1>
             <div class="row">
             <fieldset>
             </#if>
             <div class="row col-xs-6 col-sm-6 col-md-6">
                 <#include "fields/"+field.type.templatePath+".ftl">
             </div>
             <#if field.readOnly>
             <#assign counter=2>
             </#if>
             <#if counter==2>
             </fieldset>
             </div>
             <#assign counter=0>
             </#if>
        </#list>
      </form>
      </div>
      <div class="container-fluid">
      <hr/>
            <div class="btn-group btn-group-sm pull-right">
                    <label class="btn btn-primary" ng-click="doSearch(model, searchModel,1);"><i class="glyphicon glyphicon-search">&nbsp;</i>Search</label>
                    <label class="btn btn-primary" ng-click="gotoNew();"><i class="glyphicon glyphicon-plus">&nbsp;</i>New</label>
                    <label class="btn btn-primary" ng-click="gotoNew();"><i class="glyphicon glyphicon-export">&nbsp;</i>Export</label>
            </div>
        </div>
      </div>
    <br/>
    <br/>

       <div  class="container-fluid">
              <table class="table table-hover">
                  <thead>
                      <tr>
                      <#list searchFields as field>
                          <th>${field.label}</th>
                      </#list>
                          <th><div>Actions</div></th>
                      </tr>
                  </thead>
                  <tbody>
                          <tr ng-repeat="searchResult in searchPage.resource">
                              <#list searchFields as field>
                                 <td class="col-xs-${fieldSize}"><#if field.key>
                                     <#assign idField=field.id>
                                     <a ng-href="#/views${resourceName}/edit/{{searchResult.${field.id}}}">{{searchResult.${field.id}}}</a>
                                     <#elseif field.type.id == 'lookup'>
                                     <a ng-href="#/views${field.lookup}/template/{{getIdFromHref(searchResult.${field.id}.href)}}">{{searchResult.${field.id}.title  }}</a>
                                     <#else>
                                     {{searchResult.${field.id}}}
                                     </#if>
                                 </td>
                              </#list>
                                <td>
                                    <div class="btn-group btn-group-sm col-xs-3">
                                        <button class="btn btn-primary" ng-click="gotoView(searchResult.${idField});"><i class="glyphicon glyphicon-zoom-in">&nbsp;</i>View</button>
                                        <button class="btn btn-primary" ng-click="gotoEdit(searchResult.${idField});"><i class="glyphicon glyphicon-folder-open">&nbsp;</i>Edit</button>
                                        <button class="btn btn-danger" ng-click="doDelete(searchResult.${idField});"><i class="glyphicon glyphicon-remove">&nbsp;</i>Delete</button>
                                    </div>
                                </td>
                          </tr>
                  </tbody>
              </table>
              <div align="center">
                        <pagination total-items="totalResults"
                                    ng-model="currentPage"
                                    items-per-page="15"
                                    ng-change="doPage(model, searchModel, currentPage)"
                                    class="pagination-sm"
                                    previous-text="&lsaquo;"
                                    next-text="&rsaquo;"
                                    first-text="&laquo;"
                                    last-text="&raquo;">
                            </pagination>
              </div>
        </div>
     </div>
</div>