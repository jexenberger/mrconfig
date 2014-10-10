/**
 * Application declaration
 */


var levelThreeModule = angular.module('LtModule', [
    'ngRoute',
    'ngResource',
    'LtServices',
    'LtControllers',
    'ui.bootstrap'
]);

/**
 * Declaration for the AuthEvents constant, defines the following:
 * loginSuccess :
 */
var LtAuthEvents = {
   loginSuccess: 'auth-login-success',
   loginFailed: 'auth-login-failed',
   logoutSuccess: 'auth-logout-success',
   notAuthenticated: 'auth-not-authenticated',
   notAuthorized: 'auth-not-authorized'
}

var LtLoadEvents = {
    started: 'started-loading',
    finished: 'finished-loading'
}

/**
 * constant declaration
 */
levelThreeModule
    .constant('LT_AUTH_EVENTS', LtAuthEvents)
    .constant('LT_LOAD_EVENTS', LtLoadEvents);


levelThreeModule
    .config(['$httpProvider', function($httpProvider) {
        $httpProvider.interceptors.push('LtBasicAuthInterceptor');
        $httpProvider.interceptors.push('LtLoadEventsInterceptor');
    }]);



var services = angular.module('LtServices',[]);
var controllers = angular.module('LtControllers', []);


services.factory('LtBase64', function() {
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


levelThreeModule.factory('LtPromises',['$q',function($q) {


    asPromise = function(value) {
        return $q.when(value);
    }

    return {
        asPromise : asPromise,
        $get:angular.noop
    };

}]);

levelThreeModule.factory('LtHATEOSUtils',[function() {

    getIdFromHref = function(input) {
      if (input == null) {
          return null;
      }
      var parts = input.split("/");
      return parts[parts.length-1];
    }

    stripLeadingAndTrailingChars = function(aString) {
       if (aString == null) {
           return null;
       }
       aString = aString.trim();
       aString = aString.substring(1);
       aString = aString.substring(0,aString.length-1);
       return aString;
    }


    parseKeyValue = function(aString, trimLeadingTrailing) {
        var keyValue = aString.split('=');
        var key = keyValue[0].trim();
        var value = (trimLeadingTrailing != null && trimLeadingTrailing) ? stripLeadingAndTrailingChars(keyValue[1]) : keyValue[1].trim();
        return {
            key:key,
            value:value
        }
    }

    parseHeaderLink = function(linkString) {

        //<href>; type="null"; rel="1"; title="all"
        var result = {};
        var parts = linkString.split(';');
        var href = stripLeadingAndTrailingChars(parts[0]);
        var type = parseKeyValue(parts[1], true).value;
        if (type == 'null') {
            type = null;
        }
        var rel = parseKeyValue(parts[2].trim(), true).value;
        if (rel == 'null') {
            rel = null;
        }
        var title = parseKeyValue(parts[3].trim(), true).value;
        if (title == 'null') {
            title = null;
        }
        result['href'] = href;
        result['type'] = type;
        result['rel'] = rel;
        result['title'] = title;
        return result;

    }

    isLink = function(val) {
     if (val == null) {
        return false;
     }
     if (val.hasOwnProperty("href")) {
        return true;
     }

    }

    lookupById = function($http, url, $q, onSuccess, onError) {

       alert(url);

       return $http.get(url,  {
            headers : {Accept:'application/json'}
        })
        .success(function(data, status, headers, config) {
                if (onSuccess != null) {
                    onSuccess(data, headers);
                }
            })
            .error(function(error, status) {
                if (onError != null) {
                    onError(error, status);
                }
                return null;
        });


    }


    return {
        getIdFromHref:getIdFromHref,
        lookupById:lookupById,
        isLink:isLink,
        parseHeaderLink:parseHeaderLink,
        parseKeyValue:parseKeyValue,
        stripLeadingAndTrailingChars:stripLeadingAndTrailingChars
    }


}]);


levelThreeModule.factory('LtSecurityContext', ['$http','LtBase64', '$rootScope', 'LT_AUTH_EVENTS', function($http, base64, $rootScope, AUTH_EVENTS) {

   var roles = [];
   var authorization = null;
   var userName = null;

   login = function(userName, password) {
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


levelThreeModule.factory('LtBasicAuthInterceptor', ['$log', '$rootScope', function($log, $rootScope) {

    var myInterceptor = {
        // optional method
              'request': function(config) {
                // do something on success
                if ($rootScope.securityContext != null) {
                    config.headers.Authorization = 'Basic '+$rootScope.securityContext.authToken();
                }
                $log.debug(JSON.stringify(config));
                return config;
              }
    };

    return myInterceptor;
}]);


levelThreeModule.factory('LtLoadEventsInterceptor', ['$log', '$rootScope', 'LT_LOAD_EVENTS', '$q',function($log, $rootScope, LT_LOAD_EVENTS, $q) {

    var myInterceptor = {
        // optional method
              'request': function(config) {
                $log.debug(JSON.stringify(config));
                if (config.headers.Accept) {
                    $rootScope.$broadcast(LT_LOAD_EVENTS.started);
                }
                return config;
              }
              ,
              'response': function(response) {
                $rootScope.$broadcast(LT_LOAD_EVENTS.finished);
                return response;
              }
              ,
              'responseError' : function(response) {
                $rootScope.$broadcast(LT_LOAD_EVENTS.finished);
                return $q.reject(response);
              },
              'requestError' : function(response) {
                $rootScope.$broadcast(LT_LOAD_EVENTS.finished);
                return $q.reject(response);
              }

    };

    return myInterceptor;
}]);


controllers.controller('rs_menu_Controller',['$scope','$rootScope','$http', '$location', 'LT_AUTH_EVENTS', function($scope, $rootScope, $http,$location, AUTH_EVENTS) {


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


controllers.controller('reLoginModalController',['$scope','$rootScope','$http', '$location', '$modal', '$log','LtSecurityContext' ,function($scope, $rootScope, $http, $location, $modal, $log, securityContext) {



   $scope.open = function (size) {

       var modalInstance = $modal.open({
         templateUrl: 'loginScreen.html',
         controller: 'reLoginController',
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


controllers.controller('reLoginController',  [ '$scope','$modalInstance','securityContext',function ($scope, $modalInstance, securityContext) {

  $scope.securityContext = securityContext;

  $scope.ok = function (userName, password) {
    securityContext.login(userName, password);
    $modalInstance.close(userName);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
}]);


controllers.controller('reLoadingController',  [ '$scope','$rootScope','LT_LOAD_EVENTS',function ($scope, $rootScope, LT_LOAD_EVENTS) {

  $scope.show = false;

  $rootScope.$on(LT_LOAD_EVENTS.started,function(event) {
       $scope.show = true;
  });

  $rootScope.$on(LT_LOAD_EVENTS.finished,function(event) {
       $scope.show = false;
  });

}]);


controllers.controller('reLookupController',[ '$scope','$http','$modalInstance','resource','filter','filterField', function($scope, $http, $modalInstance, resource, filter, filterField) {


   $scope.filterField = filterField;

   $scope.lookup = function(value) {
       var config = {};
       var parameters = {};
       if (value != null) {
          parameters[filter] = value + "*";
       }
       var header = {}
       header["Accept"] = "application/json";
       config["params"] = parameters;
       config["headers"] = header;
       $http.get(resource, config).then(function(res){
           var results = []
           angular.forEach(res.data.result, function(item){
               results.push(item);
           });
            $scope.results =  results;
       });
   };

  $scope.ok = function (result) {
    $modalInstance.close(result);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
}]);


levelThreeModule.filter('hrefId', function() {
   return function(input) {
        return getIdFromHref(input);
   };
});

levelThreeModule.directive('defaultValue', ['$scope', '$element', '$attrs', '$parse', function($scope, $element, $attrs, $parse) {
  return {
    link: function() {
        alert('firing default value');
        var getter, setter, val;
        val = $attrs.defaultValue;
        getter = $parse($attrs.ngModel);
        setter = getter.assign;
        alert('assigning '+ngModel+" : "+val);
        setter($scope, val);
      }

  };
}]);


levelThreeModule.directive('lookupValid', ['$http', '$q','$parse','LtHATEOSUtils', function ($http, $q, $parse, ltHATEOSUtils){

   return {
      require: 'ngModel',
      link: function(scope, elem, attr, ngModel) {

          scope.$watch(attr.ngModel, function(newValue) {
                if (newValue == null) {
                    return;
                }
                if (newValue.title != null) {
                    return;
                }
                if (newValue.href == null) {
                    return;
                }
                ltHATEOSUtils.lookupById($http, newValue.href, $q,
                    function(data, headers) {
                        result = parseHeaderLink(headers('Link'));
                        newValue.title = result.title;
                        ngModel.$setValidity('lookupValid', true);

                    },
                    function(error,status) {
                        if (status == 404) {
                            ngModel.$setValidity('lookupValid', false);
                        }
                    }
                );
          });

          ngModel.$parsers.unshift(function(value) {
             if (value == null) {
                return null;
             }
             if (value.trim() == "") {
                return null;
             }
             var valid = false;
             var resourcePath = attr.lookupValid + '/' + value;

             return {href:resourcePath};
          });

          //For model -> DOM validation
          ngModel.$formatters.unshift(function(value) {
             if (value == null) {
                return null;
             }
             ngModel.$setValidity('lookupValid', true);
             return ltHATEOSUtils.getIdFromHref(value.href);
          });
      }
   };
}]);


createService = function(services, serviceName, resourcePath ) {

    services.value('resource_'+serviceName, resourcePath);

    return services.factory(serviceName,['$resource', function($resource) {
        alert(serviceName);
        return $resource(resourcePath+ '/:p_id', {} ,
        {
          'get':    {method:'GET', headers:{Accept:'application/json'}},
          'create': {method:'POST', headers:{Accept:'application/json'}},
          'save':   {method:'PUT', headers:{Accept:'application/json'}},
          'query':  {method:'GET', isArray:false, headers:{Accept:'application/json'}},
          'remove': {method:'DELETE', headers:{Accept:'application/json'}}
        });

    }]);

}








