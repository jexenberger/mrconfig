          <div class="form-group has-feedback form-group-sm">
            <label for="${field.uuid}Field">${field.label}</label>
            <div class="input-group">
                <#include 'date_type.ftl'>
            </div>
            <#include '../validation_messages.ftl'/>
          </div>