

/*
var myApplication = angular.module('myApplication', [
    'ngRoute',
    'myApplicationControllers',
    'ui.bootstrap'
]);
*/

getIdFromHref = function(input) {
  if (input == null) {
      return null;
  }
  var parts = input.split("/");
  return parts[parts.length-1];
}

var application = angular.module('application', [
    'ngRoute',
    'ngResource',
    'services',
    'controllers',
    'ui.bootstrap'
]);

application
    .constant('AUTH_EVENTS', {
        loginSuccess: 'auth-login-success',
        loginFailed: 'auth-login-failed',
        logoutSuccess: 'auth-logout-success',
        notAuthenticated: 'auth-not-authenticated',
        notAuthorized: 'auth-not-authorized'
    })

var services = angular.module('services',[]);
var controllers = angular.module('controllers', []);

services.factory('base64', function() {
    var keyStr = 'ABCDEFGHIJKLMNOP' +
        'QRSTUVWXYZabcdef' +
        'ghijklmnopqrstuv' +
        'wxyz0123456789+/' +
        '=';
    return {
        encode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            do {
                chr1 = input.charCodeAt(i++);
                chr2 = input.charCodeAt(i++);
                chr3 = input.charCodeAt(i++);

                enc1 = chr1 >> 2;
                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                enc4 = chr3 & 63;

                if (isNaN(chr2)) {
                    enc3 = enc4 = 64;
                } else if (isNaN(chr3)) {
                    enc4 = 64;
                }

                output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";
            } while (i < input.length);

            return output;
        },

        decode: function (input) {
            var output = "";
            var chr1, chr2, chr3 = "";
            var enc1, enc2, enc3, enc4 = "";
            var i = 0;

            // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
            var base64test = /[^A-Za-z0-9\+\/\=]/g;
            if (base64test.exec(input)) {
                alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
            }
            input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

            do {
                enc1 = keyStr.indexOf(input.charAt(i++));
                enc2 = keyStr.indexOf(input.charAt(i++));
                enc3 = keyStr.indexOf(input.charAt(i++));
                enc4 = keyStr.indexOf(input.charAt(i++));

                chr1 = (enc1 << 2) | (enc2 >> 4);
                chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                chr3 = ((enc3 & 3) << 6) | enc4;

                output = output + String.fromCharCode(chr1);

                if (enc3 != 64) {
                    output = output + String.fromCharCode(chr2);
                }
                if (enc4 != 64) {
                    output = output + String.fromCharCode(chr3);
                }

                chr1 = chr2 = chr3 = "";
                enc1 = enc2 = enc3 = enc4 = "";

            } while (i < input.length);

            return output;
        }
    };
});

application.factory('securityContext', ['$http','base64', '$rootScope', 'AUTH_EVENTS', function($http, base64, $rootScope, AUTH_EVENTS) {

   var roles = [];
   var authorization = null;
   var userName = null;

   login = function(userName, password) {
        alert(JSON.stringify(base64));
        authorization = base64.encode(userName + ':' + password);
        $http.get("/roles").success(function(data) {
            roles = data.roles;
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
        });
   }

   userName = function() {
        return userName;
   }

   isInRole = function(role) {
        return roles.indexOf(role) > -1;
   }

   logout = function() {
        authorization = null;
        roles = []
   }

   isLoggedIn = function() {
        return authorization != null;
   }

   authToken = function() {
        return authorization;
   }

   var context = {
    isInRole : isInRole,
    login : login,
    isLoggedIn : isLoggedIn,
    logout : logout,
    authToken : authToken,
    userName : userName
   };

   $rootScope.securityContext = context;
   return context;
}]);



isLink = function(val) {
 if (val == null) {
    return false;
 }
 if (val.hasOwnProperty("href")) {
    return true;
 }

}

lookupById = function($http, url, onSuccess, onError) {

    return $http.get(url)
        .success(function(data) {
            alert('got data');
            if (onSuccess != null) {
                onSuccess(data);
            }
            return data;
        })
        .error(function(error, status) {
            alert(status);
            if (onError != null) {
                onError(error, status);
            }
            return null;
        });
}


application.factory('basicAuthInterceptor', ['$log', '$rootScope', function($log, $rootScope) {

    var myInterceptor = {
        // optional method
              'request': function(config) {
                // do something on success
                if ($rootScope.securityContext != null) {
                    config.headers.Authorization = 'Basic '+$rootScope.securityContext.authToken();
                }
                $log.debug(JSON.stringify(config));
                return config;
              },
    };

    return myInterceptor;
}]);

application.config(['$httpProvider', function($httpProvider) {

    $httpProvider.interceptors.push('basicAuthInterceptor');
}]);

controllers.controller('rs_menu_Controller',['$scope','$rootScope','$http', '$location', 'AUTH_EVENTS', function($scope, $rootScope, $http,$location, AUTH_EVENTS) {


    loadMenu = function() {
        $http.get("/menus").success(function(data) {
            var groups = [];
            for (var group in data.menuGroups) {
                 var menuItem = {};
                 menuItem.name = group
                 menuItem.items = data.menuGroups[group];
                 groups.push(menuItem);
            }
            $rootScope.menu = groups;
        });
    }

    $rootScope.$on(AUTH_EVENTS.loginSuccess,function(event) {
        loadMenu();
    });

}]);


controllers.controller('rs_modal_controller',['$scope','$rootScope','$http', '$location', '$modal', '$log','securityContext' ,function($scope, $rootScope, $http, $location, $modal, $log, securityContext) {



   $scope.open = function (size) {

       var modalInstance = $modal.open({
         templateUrl: 'loginScreen.html',
         controller: LoginController,
         size: size,
         resolve: {
           securityContext: function () {
             return securityContext;
           }
         }
       });

       modalInstance.result.then(function (userName) {
         $scope.userNameDisplay = userName;
       }, function () {
         $log.info('Modal dismissed at: ' + new Date());
       });
   };

   $scope.login = function(userName, password) {
        securityContext.login(userName, password);
        $scope.userIdDisplay = userName;
   }

   if (!securityContext.isLoggedIn()) {
        $scope.open();
   }



}]);


var LoginController = function ($scope, $modalInstance, securityContext) {

  $scope.securityContext = securityContext;

  $scope.ok = function (userName, password) {
    securityContext.login(userName, password);
    $modalInstance.close(userName);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
};


controllers.controller('reLookupController',[ '$scope','$modalInstance','resource','filter','filterField', function($scope, $modalInstance, resource, filter, filterField) {


   $scope.filterField = filterField;

   $scope.lookup = function(value) {
       var config = {};
       var parameters = {};
       parameters[filter] = value + "*";
       var header = {}
       header["Accept"] = "application/json";
       config["params"] = parameters;
       config["headers"] = header;
       $scope.results = $http.get(resource, config).then(function(res){
           var results = []
           angular.forEach(res.data.result, function(item){
               results.push(item);
           });
           return results;
       });
   };

  $scope.ok = function (result) {
    $modalInstance.close(result);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
}]);


application.filter('hrefId', function() {
   return function(input) {
        return getIdFromHref(input);
   };
});


application.directive('lookupValid', ['$http', function ($http){
   return {
      require: 'ngModel',
      link: function(scope, elem, attr, ngModel) {
          //For DOM -> model validation
          ngModel.$parsers.unshift(function(value) {
             var valid = false;
             var resourcePath = attr.lookupValid + '/' + value;
             var result = lookupById($http, resourcePath);
             valid = (result != null);
             ngModel.$setValidity('lookupValid', valid);
             return valid ? result : undefined;
          });

          //For model -> DOM validation
          ngModel.$formatters.unshift(function(value) {
             if (value == null) {
                ngModel.$setValidity('lookupValid', true);
             }
             return value.id;
          });
      }
   };
}]);








