          <div class="form-group has-feedback form-group-sm">
            <label for="${field.uuid}Field">${field.label}</label>
            <div class="input-group">
                <#include 'lookup_type.ftl'>
            </div>
            <span ng-show="model.${fieldId}.title != null" class="help-block has-success" ng-init="${field.uuid}OverrideHelp = true"><h6>{{(model.${fieldId}.title != null) ? model.${fieldId}.title : '${field.helpText}'}}</h6></span>
            <#include '../validation_messages.ftl'/>
          </div>
