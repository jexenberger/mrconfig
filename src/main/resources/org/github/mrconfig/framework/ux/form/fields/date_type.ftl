            <input type="text"
                   class="form-control"
                   placeholder="Enter ${field.label}"
                   datepicker-popup="{{format}}"
                   ng-model="model.${field.id}"
                   is-open="opened"
                   datepicker-options="dateOptions"
                   close-text="Close"
                   <#include '../constraints.ftl'>/>

                   <span class="input-group-btn">
                        <button type="button" class="btn btn-default" ng-click="open($event)"><i class="glyphicon glyphicon-calendar"></i></button>
                   </span>
