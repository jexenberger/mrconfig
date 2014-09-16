<#assign capture=false>
<div ng-controller="${id}Controller">
    <div class="row" >
        <div class="container col-md-10 col-md-offset-1">
            <legend><h4>${name}<small> - search</small></h4></legend>
            <form novalidate class="form-horizontal" role="form">
            <#list searchFields as field>
              <div class="form-group">
                 <#include "fields/"+field.type.templatePath+".ftl">
              </div>
            </#list>
            </form>
            <div class="btn-group btn-group-sm pull-right">
                    <label class="btn btn-primary glyphicon glyphicon-search" ng-click="doSearch(model, searchModel,1);">&nbsp;Search</label>
                    <label class="btn btn-primary glyphicon glyphicon-plus" ng-click="gotoNew();">&nbsp;New</label>
                    <label class="btn btn-primary glyphicon glyphicon-export" ng-click="gotoNew();">&nbsp;Export</label>
            </div>
        </div>
    </div>
    <br/>
    <br/>
    <div class="row" ng-if="searchPage != null">
       <div class="container col-md-10 col-md-offset-1">
        <div  class="panel panel-default">
            <div class="panel-heading">Search Results</div>
              <table class="table table-hover">
                  <thead>
                      <tr>
                      <#list searchFields as field>
                          <th>${field.label}</th>
                      </#list>
                          <th>Delete</th>
                      </tr>
                  </thead>
                  <tbody>
                          <tr ng-repeat="searchResult in searchPage.resource">
                              <#list searchFields as field>
                                 <td><#if field.key>
                                     <#assign idField=field.id>
                                     <a ng-href="#${resourceName}/edit/{{searchResult.${field.id}}}">{{searchResult.${field.id}}}</a>
                                     <#elseif field.type.id == 'lookup'>
                                     {{searchResult.${field.id}.href}}
                                     <#else>
                                     {{searchResult.${field.id}}}
                                     </#if>
                                 </td>
                              </#list>
                                <td>
                                    <div class="btn-group">
                                        <button class="btn btn-danger glyphicon glyphicon-remove" ng-click="doDelete(searchResult.${idField});">&nbsp;Delete</button>
                                    </div>
                                </td>
                          </tr>
                  </tbody>
              </table>

            <div class="panel-footer center-block">
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
              <div class="container">
            </div>
     </div>
</div>
