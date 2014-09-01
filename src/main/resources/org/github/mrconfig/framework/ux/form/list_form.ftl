<#assign capture=false>
<div class="panel-heading clearfix" ng-controller="${id}Controller">
     <h2 class="panel-title pull-left" style="padding-top: 7.5px;">${name}</h2>

</div>
     <div class="panel-body">
        <form novalidate class="form-horizontal" role="form">
        <#list searchFields as field>
          <div class="form-group">
             <#include "fields/"+field.type.templatePath+".ftl">
          </div>
        </#list>
        </form>
        <div class="btn-group">
                <label class="btn btn-primary" ng-click="doSearch(model, searchModel,1);">Search</label>
                <label class="btn btn-primary" ng-click="gotoNew();">New</label>
        </div>
        <br/>
        <div>
        <table class="table table-striped" ng-if="searchPage != null">
              <thead>
                  <tr>
                  <#list searchFields as field>
                      <th>${field.label}</th>
                  </#list>
                  </tr>
              </thead>
              <tbody>
                      <tr ng-repeat="searchResult in searchPage.resource">
                          <#list searchFields as field>
                             <td><#if field.key>
                                 <button class="btn btn-danger" ng-click="doDelete(searchResult.${field.id});">Delete</button>&nbsp;
                                 <a ng-href="#${resourceName}/edit/{{searchResult.${field.id}}}">{{searchResult.${field.id}}}</a>
                                 <#elseif field.type.id == 'lookup'>
                                 {{searchResult.${field.id}.href}}
                                 <#else>
                                 {{searchResult.${field.id}}}
                                 </#if>
                             </td>
                          </#list>
                      </tr>
              </tdbody>
        </table>
        <div align="center" ng-hide="searchPage == null">
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
