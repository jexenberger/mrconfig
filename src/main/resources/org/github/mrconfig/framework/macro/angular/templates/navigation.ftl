application.config(function($routeProvider, $locationProvider) {
		$routeProvider
        <#list forms as form>
                .when('${form.resourceName}/list', {
                    templateUrl : '/views${form.resourceName}/list.html',
                    controller  : '${form.id}Controller'
                })
                .when('${form.resourceName}/edit/:p_id', {
                    templateUrl : '/views${form.resourceName}/edit.html',
                    controller  : '${form.id}Controller'
                })
                .when('${form.resourceName}/new', {
                    templateUrl : '/views${form.resourceName}/edit.html',
                    controller  : '${form.id}Controller'
                })
        </#list>
	});