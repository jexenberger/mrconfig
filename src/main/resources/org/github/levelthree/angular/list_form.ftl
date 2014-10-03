<#assign capture=false>
<div ng-controller="${uxContext['list']}" ng-cloak>
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
             <div class="col-xs-6 col-sm-6 col-md-6">
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
                    <label class="btn btn-primary glyphicon glyphicon-search" ng-click="doSearch(model, searchModel,1);">&nbsp;Search</label>
                    <label class="btn btn-primary glyphicon glyphicon-plus" ng-click="gotoNew();">&nbsp;New</label>
                    <label class="btn btn-primary glyphicon glyphicon-export" ng-click="gotoNew();">&nbsp;Export</label>
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
                                 <td><#if field.key>
                                     <#assign idField=field.id>
                                     <a ng-href="#${resourceName}/edit/{{searchResult.${field.id}}}">{{searchResult.${field.id}}}</a>
                                     <#elseif field.type.id == 'lookup'>
                                     <a ng-href="#${field.lookup}/view/{{getIdFromHref(searchResult.${field.id}.href)}}">{{searchResult.${field.id}.title  }}</a>
                                     <#else>
                                     {{searchResult.${field.id}}}
                                     </#if>
                                 </td>
                              </#list>
                                <td>
                                    <div class="btn-group btn-group-sm">
                                        <button class="btn btn-primary glyphicon glyphicon-zoom-in" ng-click="gotoView(searchResult.${idField});">&nbsp;View</button>
                                        <button class="btn btn-primary glyphicon glyphicon-folder-open" ng-click="gotoEdit(searchResult.${idField});">&nbsp;Edit</button>
                                        <button class="btn btn-danger glyphicon glyphicon-remove" ng-click="doDelete(searchResult.${idField});">&nbsp;Delete</button>
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