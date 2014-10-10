<#macro field_wrapper fieldInclude field>
            <div class="form-group has-feedback form-group-sm">
              <#include 'fields/default_label.ftl'/>
                  <div class="col-xs-6">
                  <#include 'fields/${fieldInclude}.ftl'>
                  </div>
                  <div class="col-xs-4">
                  <#include 'validation_messages.ftl'/>
                  </div>
            </div>
</#macro>