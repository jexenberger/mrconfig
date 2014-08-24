

createService = function(services, serviceName, resourcePath ) {

    services.factory(serviceName,['$resource', function($resource) {

        return $resource(resourcePath+ '/:p_id', {} ,
        {
          'get':    {method:'GET', headers:{Accept:'application/json'}},
          'create':   {method:'POST', headers:{Accept:'application/json'}},
          'save':   {method:'PUT', headers:{Accept:'application/json'}},
          'query':  {method:'GET', isArray:false, headers:{Accept:'application/json'}},
          'remove': {method:'DELETE', headers:{Accept:'application/json'}},
          'remove': {method:'DELETE', headers:{Accept:'application/json'}}
        });
        
    }]);

}

createGenericController = function(module, controllerName, serviceName, resourceName, formName) {

    module.controller(controllerName,['$scope', '$routeParams','$window', '$http', '$location', serviceName, function($scope, $routeParams, $window, $http, $location, service) {
      alert(JSON.stringify($routeParams));
      $scope.resourceName = resourceName;
      $scope.master = {};
      $scope.isNew = ($routeParams.p_id == null);
      $scope.currentPage = 1;
      $scope.totalResults = 0;
      $scope.alerts = [];

      $scope.load = function() {
        service.get({p_id:$routeParams.p_id}, function(result) {
            $scope.master = result;
            $scope.model = angular.copy($scope.master);
        });
      }
    
      if (!$scope.isNew) {
        $scope.load();
      }
    
      $scope.open = function($event) {
          $event.preventDefault();
          $event.stopPropagation();
    
          $scope.opened = true;
        };
    
    
    
    
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
             $scope.alerts.push({ type: 'danger', msg: 'There was an error creating the record'});
             if (error.errors != null) {
                for (i=0;i < error.errors.length;i++) {
                  $scope.alerts.push({ type: 'danger', msg: error.errors[i].description});
                }
             }
           });
        } else {
           service.save( model,function(success) {
             $scope.master = success;
             $scope.model = angular.copy($scope.master);
             $scope.alerts.push({ type: 'success', msg: 'Record Saved' })
           },function(error) {
             $scope.alerts.push({ type: 'danger', msg: 'There was an error saving the record' })
           });
        }
      };
    
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
    
    
      $scope.reset = function() {
        $scope.model = angular.copy($scope.master);
      };
    
      $scope.isUnchanged = function(model) {
        return angular.equals(model, $scope.master);
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

      $scope.fieldValid = function(field) {
        console.log(field.$valid);
        return  field.$valid;
      };
    
      $scope.fieldInvalid = function(field) {
        console.log(JSON.stringify(field));
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

      $scope.doDelete = function(id) {
        $scope.alerts = [];
        service.remove({p_id:id}, function(success) {
               $scope.alerts.push({ type: 'success', msg: 'recorded removed' });
               $scope.doSearch($scope.model, $scope.searchModel, 1);
        });

      }


    
      $scope.reset();
    
    }]);

}