                             <#if !field.readOnly>
                             <span ng-show="${id}Form.${field.uuid}Name.$valid && ${field.uuid}OverrideHelp != true" class="has-success"><h6>${field.helpText}</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.required" class="has-warning"><h6>Required</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.max" class="help-block has-warning"><h6>Field  allowed</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.min" class="help-block has-warning"><h6>Field  allowed value</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.minlength" class="help-block has-warning"><h6>Field allowed length</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.maxlength" class="help-block has-warning"><h6>Field than allowed length</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.number" class="help-block has-warning"><h6>Field should be numeric</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.pattern" class="help-block has-warning"><h6>Field contains invalid characters</h6></span>
                             <span ng-show="${id}Form.${field.uuid}Name.$error.lookupValid" class="help-block has-warning"><h6>Not found</h6></span>
                             </#if>
