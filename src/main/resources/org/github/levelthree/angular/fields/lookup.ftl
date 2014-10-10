            <div class="form-group has-feedback form-group-sm">
              <#include 'default_label.ftl'/>
                  <div class="col-xs-4">
                  <#include 'lookup_type.ftl'>
                  </div>
                  <div class="col-xs-3">
                  <span ng-show="model.${fieldId}.title != null" class="help-block has-success" ng-init="${field.uuid}OverrideHelp = true"><h6>{{(model.${fieldId}.title != null) ? model.${fieldId}.title : '${field.helpText}'}}</h6></span>
                  </div>
                  <div class="col-xs-4">
                  <#include '../validation_messages.ftl'/>
                  </div>
            </div>
