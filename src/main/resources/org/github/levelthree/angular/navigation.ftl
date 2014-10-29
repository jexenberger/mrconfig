application.config(function($routeProvider, $locationProvider) {
		$routeProvider
        <#list resources as resource>
                .when('${resource.path}/list.html', {
                    templateUrl : '${resource.path}/list.html',
                    controller  : '${resource.name}Controller',
                    resolve : {
                        editable : function() {
                           return true;
                        }
                    }

                })
                .when('${resource.path}/edit/:p_id', {
                    templateUrl : '${resource.path}/put.html',
                    controller  : '${resource.name}Controller',
                    resolve : {
                        editable : function() {
                           return true;
                        }
                    }
                })
                .when('${resource.path}/template/:p_id', {
                    templateUrl : '${resource.path}/put.html',
                    controller  : '${resource.name}Controller',
                    resolve : {
                        editable : function() {
                           return false;
                        }
                    }

                })
                .when('${resource.path}/new.html', {
                    templateUrl : '${resource.path}/post.html',
                    controller  : '${resource.name}Controller',
                    resolve : {
                        editable : function() {
                           return true;
                        }
                    }

                })
        </#list>
	});