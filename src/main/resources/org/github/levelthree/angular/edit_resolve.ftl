{
                        editable : function() {
                           return true;
                        },
                        model : function() {
                            var id = $route.current.params.p_id;
                            return $injector.get('${serviceName}').get(id);
                        }
                    }