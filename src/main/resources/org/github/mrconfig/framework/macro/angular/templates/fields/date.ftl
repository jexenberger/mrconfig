          <label for="${field.id}" class="col-sm-2 control-label">${field.label}</label>
          <div class="col-sm-9">
            <p class="input-group">
            <input type="text" class="form-control" datepicker-popup="{{format}}" ng-model="model.${field.id}" is-open="opened"  datepicker-options="dateOptions"   close-text="Close" <#if field.required && capture>required</#if>/>

                        <span class="input-group-btn">
                            <button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
                        </span>
            </p>
          </div>