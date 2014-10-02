





LtBaseController = function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {

      var _this = this;

      $scope.alerts = [];

      $scope.getIdFromHref = getIdFromHref;

      $scope.processError = function(error, ref) {
          var message = '';

          switch (error.status) {
            case 500: message = 'An error has occured during this operation';
                      break;
            case 401: message = 'You are not Authorized to view this record';
                      break;
            case 405: message = 'You cannot perform the operation you requested for this record';
                      break;
            case 400: message = 'There was a problem with the data that was submitted for this record';
                      break;
            case 404: message = 'The record requested no longer exists';
                      break;
          }
          $scope.flashError(message);
          if (error.data.errors != null) {
            for (i=0;i < error.data.errors.length;i++) {
                $scope.flashError( error.data.errors[i].description);
            }
          } else {
             $scope.flashError(JSON.stringify(error));
          }

      }

      $scope.open = function(scopeName, $event, $index) {
          $event.preventDefault();
          $event.stopPropagation();
          if ($scope.state[scopeName] == null) {
            $scope.state[scopeName] = {}
          }
          if ($index >= 0) {
            if ($scope.state[scopeName][$index] == null) {
                $scope.state[scopeName][$index] = {};
            }
            $scope.state[scopeName][$index]['open'] = true;
          } else {
            if ($scope.state[scopeName] == null) {
                $scope.state[scopeName] = {};
            }
            $scope.state[scopeName]['open'] = true;

          }

      };

      $scope.isDateFieldOpen = function(scopeName, index) {
        if ($scope.state[scopeName] == null) {
           return false;
        }
        if (index >= 0 && $scope.state[scopeName].length > index) {
           return false;
        }
        return $scope.state[index][scopeName].open;
      }

       $scope.removeCollectionItem = function(collectionField, index) {
              $scope.model[collectionField].splice(index, 1);
       };



      $scope.clearFlash = function() {
        $scope.alerts = [];
      }

      $scope.flash = function(type, message) {
        $scope.alerts.push({ type: type, msg: message});
      }

      $scope.flashError = function(message) {
        $scope.flash('danger', message);
      }

      $scope.flashInfo = function(message) {
        $scope.flash('success', message);
      }

      $scope.flashWarning = function(message) {
        $scope.flash('warning', message);
      }


      $scope.lookup = function(resource,filter,value) {
            var config = {};
            var parameters = {};
            parameters[filter] = value + "*";
            var header = {}
            header["Accept"] = "application/json";
            config["params"] = parameters;
            config["headers"] = header;
            return $http.get(resource, config).then(function(res){
                var results = []
                angular.forEach(res.data.result, function(item){
                    results.push(item);
                });
                return results;
            });
      };
    
    
      $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
      };

      $scope.goto = function(path) {
        $location.path(path);
      }
    
      $scope.gotoList = function() {
        $scope.goto(resourceName+'/list.html');
      };
    
      $scope.gotoNew = function() {
        $scope.goto(resourceName+'/new.html');
      };

      $scope.gotoView = function(id) {
        $scope.goto(resourceName+'/view/'+id);
      };

      $scope.gotoEdit = function(id) {
        $scope.goto(resourceName+'/view/'+id);
      };

      $scope.fieldValid = function(field) {
        return  field.$valid;
      };
    
      $scope.fieldInvalid = function(field) {
        return field;
      };

      $scope.fieldPristine = function(field) {
        return field.$pristine
      };

      $scope.validationClass = function(field) {
        if (field == null) {
          return null;
        }
        if (field.$pristine) {
           return null;
        }
        if (field.$valid) {
           return 'has-success';
        }
        if (field.$invalid) {
           return 'has-error';
        }
      }



      $scope.reset();
    
    }]);


LtListController = function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {


       angular.extend(this, new LtBaseController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));

       $scope.currentPage = 1;
       $scope.totalResults = 0;


       $scope.doPage = function(model, searchModel, page) {
          $scope.doSearch(model, searchModel, page);
       }

       $scope.doSearch = function(model, searchModel, page) {
             for (key in searchModel) {
                 model[key] =  searchModel[key].rel;
             }
             for (key in model) {
                 if (model[key] == null) {
                     continue;
                 }
                 if (isLink(model[key])) {
                    model[key] = model[key].href.split("/")[1];
                 }
                 if (model[key] != null && model[key].toString().trim() == '') {
                     model[key] = null;
                 }
             }
             model["details"] = true;
             model['page'] = page;
             service.query(model, function(success) {
                $scope.searchPage = success;
                $scope.totalResults = $scope.searchPage.totalResults;

             });

       }


       $scope.doDelete = function(id) {
            $scope.alerts = [];
            var result = confirm('Are you sure you want to delete this record');
            if (!result) {
                return;
            }
            service.remove({p_id:id}, function(success) {
                   $scope.flash('success', 'record removed' );
                   $scope.doSearch($scope.model, $scope.searchModel, 1);
            });
      }


}]);

LtEditController = function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {

      angular.extend(this, new LtBaseController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));


      $scope.state = {};
      $scope.resourceName = resourceName;
      $scope.master = {};
      $scope.isNew = ($routeParams.p_id == null);

      $scope.load = function() {
        service.get({p_id:$routeParams.p_id}, function(result) {
            $scope.master = result;
            $scope.model = angular.copy($scope.master);
        }, function(error) {
            $scope.processError(error,$routeParams.p_id);
        });
      }

      if (!$scope.isNew) {
           $scope.load();
      }

      $scope.reset = function() {
        $scope.model = angular.copy($scope.master);
      };

      $scope.isUnchanged = function(model) {
        return angular.equals(model, $scope.master);
      };

      $scope.addToCollection = function(modelFieldName) {
         if ($scope.model[modelFieldName] == null) {
            $scope.model[modelFieldName] = [];
         }
         $scope.model[modelFieldName].push({});
      }

      $scope.applyAction = function(link,rel) {
        var relParts = rel.split(":");
        var relationShip = rel[0];
        var action = rel[1];
        if (relationShip == 'self') {
           //we need to call the action, then trigger a refresh
           var uriParts = link.split("?");
           var queryParams = uriParts[1].split("&");
           var applyParms = {};
           for (i=0;i<queryParams.length;i++) {
              var paramParts = queryParams.split("=");
              $scope.model[paramParts[0]] = paramParts[1];
           }

           if (action == 'PUT') {
               $scope.update($scope.model);
           }

        }
      }


      $scope.update = function(model) {
        $scope.alerts = [];
        if ($scope[formName].$invalid) {
            $scope.alerts.push({ type: 'danger', msg: 'Unable to save record while errors exist' });
            return;
        }
        if ($scope.isNew) {
           service.create(model,function(success) {
             $scope.master = success;
             $scope.model = angular.copy($scope.master);
             $scope.alerts.push({ type: 'success', msg: 'Record Created' });
             $scope.isNew = false;
           },function(error) {
             $scope.processError(error, null);
           });
        } else {
           service.save( model,function(success) {
             $scope.master = success;
             $scope.model = angular.copy($scope.master);
             $scope.alerts.push({ type: 'success', msg: 'Record Saved' })
           },function(error) {
             $scope.processError(error, null);
           });
        }
      };

}];

LtModalController =  function($scope, $routeParams, $window, $http, $location, $injector, $parse, service) {

      angular.extend(this, new BaseController($scope, $routeParams, $window, $http, $location, $injector, $parse, service));

      $scope.openLookup = function(resource, filter, filterField, modelFieldName, helpDisabledFlag) {

             var $modal = injector.get('$modal');
             var modalInstance = $modal.open({
               templateUrl: 'lookupModal.html',
               controller: 'reLookupController',
               size: 'lg',
               resolve: {
                 resource: function () {
                   return resource;
                 },
                 filter: function () {
                   return filter;
                 },
                 filterField: function () {
                   return filterField;
                 }
               }
             });
              modalInstance.result.then(function (result) {
                 var model = $parse(modelFieldName);
                 model.assign($scope, result);
                 $scope.apply();
              },
              function () {
                 $log.info('Modal dismissed at: ' + new Date());
              });


      }



}]);
