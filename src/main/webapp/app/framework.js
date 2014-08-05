

createService = function(services, serviceName, resourcePath ) {

    services.factory(serviceName,['$resource', function($resource) {

        return $resource(resourcePath+'/:p_id',{
            p_id: '@id'
        },
        {
          'get':    {method:'GET', headers:{Accept:'application/json'}},
          'create':   {method:'POST', headers:{Accept:'application/json'}},
          'save':   {method:'PUT', headers:{Accept:'application/json'}},
          'query':  {method:'GET', isArray:false, headers:{Accept:'application/json'}},
          'remove': {method:'DELETE', headers:{Accept:'application/json'}},
          'delete': {method:'DELETE', headers:{Accept:'application/json'}}
        });
        
    }]);

}

createGenericController = function(module, controllerName, serviceName, resourceName) {

    module.controller(controllerName,['$scope', '$routeParams','$http', '$location', serviceName, function($scope, $routeParams, $http,$location, service) {
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
        alert(JSON.stringify(model));
        if ($scope.isNew) {
           service.create(model,function(success) {
             $scope.master = angular.copy(model);
             $scope.alerts = [];
             $scope.alerts.push({ type: 'success', msg: 'Record Saved' })
             $scope.isNew = false;
           },function(error) {
             $scope.alerts.push({ type: 'danger', msg: 'There was an error saving the record:\n<b>'+JSON.stringify(error.data) }+"</b>")
           });
        } else {
           service.save({p_id:model.id}, model,function(success) {
             $scope.master = angular.copy(model);
             $scope.alerts = [];
             $scope.alerts.push({ type: 'success', msg: 'Record Saved' })
           },function(error) {
             $scope.alerts.push({ type: 'danger', msg: 'There was an error saving the record' })
           });
        }
      };
    
      $scope.doPage = function(model, searchModel) {
         $scope.doSearch(model, searchModel);
      }
    
    
    
      $scope.doSearch = function(model, searchModel) {
            for (key in searchModel) {
                model[key] =  searchModel[key].rel;
            }
            for (key in model) {
                if (model[key] != null && model[key].toString().trim() == '') {
                    model[key] = null;
                }
            }
            model["details"] = true;
            model['page'] = $scope.currentPage;
            $scope.searchPage = service.query(model, function(success) {
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
    
      $scope.gotoList = function(location) {
        $location.path(location);
      };
    
      $scope.gotoNew = function(resource) {
        $location.path(resource+'/new');
      };
    
    
    
      $scope.reset();
    
    }]);

}