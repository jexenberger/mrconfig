

/*
var myApplication = angular.module('myApplication', [
    'ngRoute',
    'myApplicationControllers',
    'ui.bootstrap'
]);
*/
var application = angular.module('application', [
    'ngRoute',
    'ngResource',
    'services',
    'controllers',
    'ui.bootstrap'
]);

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

var securityContext = application.factory('securityContext', 'base64', function($http, base64) {

   var securityContext = {};

   securityContext.roles = [];

   securityContext.authorization = null;

   securityContext.login = function(userName, password) {
        securityContext.authorization = Base64.encode(username + ':' + password);
        $http.get("/roles").success(function(data) {
            roles = data.roles;
        });
   }

   securityContext.isInRole = function(role) {
        return securityContext.roles.indexOf(role) > -1;
   }

   securityContext.logout = function() {
        securityContext.authorization = null;
        securityContext.roles = []
   }

   securityContext.isLoggedIn = function() {
        return securityContext.authorization != null;
   }

   return securityContext;
})

isLink = function(val) {
 if (val == null) {
    return false;
 }
 if (val.hasOwnProperty("href")) {
    return true;
 }

}


application.factory('basicAuthInterceptor', ['$log', '$rootScope','securityContext', function($log, $rootScope, securityContext) {

    var myInterceptor = {
        // optional method
              'request': function(config) {
                // do something on success
                 $log.debug(JSON.stringify(config));
                config.headers.Authorization = 'Basic YWRtaW46cGFzc3dvcmQ='
                return config;
              },
    };

    return myInterceptor;
}]);

application.config(['$httpProvider', function($httpProvider) {

    $httpProvider.interceptors.push('basicAuthInterceptor');
}]);

controllers.controller('rs_menu_Controller',['$scope','$rootScope','$http', '$location',  function($scope, $rootScope, $http,$location) {

    if($rootScope.menu == null) {

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

       modalInstance.result.then(function (selectedItem) {
         $scope.selected = selectedItem;
       }, function () {
         $log.info('Modal dismissed at: ' + new Date());
       });
   };

   $scope.login = function(userName, password) {
        securityContext.login(userName, password);
   }


}]);


var LoginController = function ($scope, $modalInstance, securityContext) {

  $scope.securityContext = securityContext;

  $scope.ok = function () {
    $modalInstance.close($scope.selected.item);
  };

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
};








