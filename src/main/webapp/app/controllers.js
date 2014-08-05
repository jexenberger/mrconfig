var mrConfigurerControllers = angular.module('mrConfigurerControllers', []);

var lastSelectedValue = null;

var AddEnvironmentController = function ($scope, $modalInstance) {

  $scope.environment = {
    type : 'environment'
  };


  $scope.ok = function () {
    $modalInstance.close($scope.environment);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };

  $scope.isNotServer = function() {
    var result =  !($scope.environment.type == 'vm' || $scope.environment.type == 'physical');
    console.log($scope.environment.type);
    return result;
  }
};

getImageUrl = function(type) {

    return "app-images/"+type+".png";
}

mrConfigurerControllers.controller('OverviewController',['$scope', '$http', function ($scope, $http) {

 var options = new primitives.orgdiagram.Config();

 $http.get('data/environments.json?date_='+new Date().getMilliseconds())
 .success(function(data) {

     var items = [];
     for (i=0; i <data.length;i++) {
        console.log(JSON.stringify(data[i]));
        var item = new primitives.orgdiagram.ItemConfig({
                                id: data[i].id,
                                parent: data[i].parent,
                                title: data[i].name,
                                description: data[i].location,
                                image: getImageUrl(data[i].type),
                                itemTitleColor: primitives.common.Colors.Black,
                                context : $scope

                            });
        items.push(item);
     }
     options.items = items;
     options.hasSelectorCheckbox = primitives.common.Enabled.False;
     $scope.options = options;
     console.log('complete');

  });

  $scope.add = function() {

    var modalInstance = $modal.open({
         templateUrl: 'addEnvironment.html',
         controller: AddEnvironmentController,
         size: null

       });

       modalInstance.result.then(function (environment) {
         $scope.environment = environment;

         console.log(JSON.stringify($scope.options));
       }, function () {
         console.log('Modal dismissed at: ' + new Date());
       });
   }




}]);






