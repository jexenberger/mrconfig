<#assign capture=false>
<div class="col-md-15" ng-controller="${id}Controller">
  <div class="panel panel-primary">
    <div class="panel-heading">${name}</div>
    <div class="panel-body">
        <form novalidate class="form-horizontal" role="form">
        <#list searchFields as field>
          <div class="form-group">
             <#include "fields/"+field.type.templatePath>
          </div>
        </#list>
        </form>
        <div class="btn-group">
                <label class="btn btn-primary" ng-click="doSearch(model, searchModel);">Search</label>
                <label class="btn btn-primary" ng-click="gotoNew('${resourceName}');">New</label>
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
                          <#switch field.type>
                             <#case "Id">
                             <td><a ng-href="#${resourceName}/edit/{{searchResult.${field.id}}}">{{searchResult.${field.id}}}</a></td>
                             <#break>
                             <#case "Lookup">
                             <td>{{searchResult.${field.id}.title}}</td>
                             <#break>
                             <#default>
                             <td>{{searchResult.${field.id}}}</td>
                             <#break>
                          </#switch>
                          </#list>
                      </tr>
              </tdbody>
        </table>
        <div align="center" ng-hide="searchPage == null"><pagination total-items="totalResults" ng-model="currentPage" max-size="10" ng-change="doPage(model, searchModel)" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;"></pagination></div>

    </div>
    </div>
  </div>
</div>
