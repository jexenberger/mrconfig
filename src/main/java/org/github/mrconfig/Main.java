package org.github.mrconfig;

import org.github.levelthree.Module;
import org.github.levelthree.Resource;
import org.github.levelthree.activerecord.jpa.JPAModule;
import org.github.levelthree.angular.AngularResourceUX;
import org.github.levelthree.angular.AngularUXModule;
import org.github.levelthree.resources.JaxbProvider;
import org.github.levelthree.resources.RolesResources;
import org.github.levelthree.resources.SessionInViewInterceptor;
import org.github.levelthree.resources.StaticResource;
import org.github.levelthree.ux.form.BeanFormBuilder;
import org.github.levelthree.ux.form.Form;
import org.github.levelthree.ux.form.FormBuilder;
import org.github.mrconfig.domain.RoleMapping;
import org.github.mrconfig.resources.*;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import static org.github.levelthree.ux.ClasspathSource.classpath;
import static org.github.levelthree.ux.StaticView.staticView;

/**
 * Created by w1428134 on 2014/07/16.
 */
public class Main extends Module {
    @Override
    public void init() {
        StaticResource.setRelativeDebugPath("/src/main/webapp");


                        /*
                        ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
                        scanner.setResourcePackage("org.github.mrconfig.resources"); //your "resources" package
                        ClassReaders.setReader(new DefaultJaxrsApiReader());

                        // Set the swagger config options
                        SwaggerConfig config = ConfigFactory.config();
                        config.setApiVersion("1.0.3");
                        config.setBasePath("http://localhost:6001"); //your "

                        ClassReaders.setReader(new DefaultJaxrsApiReader());


                        addResourceClass(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
                        addResourceClass(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
                        addResourceClass(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
                        addResourceClass(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);
                        */

        this.addModule(new JPAModule("org.github.mrconfig.domain"));
        this.addModule(new JaxbProvider("org.github.mrconfig.domain"));
        AngularUXModule uxModule = new AngularUXModule();
        this.addModule(uxModule);


        addModule(new Module("Environment") {
            @Override
            public void init() {
                register(uxModule.scaffold(this, EnvironmentResource.class));
                register(uxModule.scaffold(this,DataCentreResource.class));
                register(uxModule.scaffold(this,EnvironmentGroupResource.class));
                register(uxModule.scaffold(this,ServerResource.class));
            }
        });

        addModule(new Module("AccessManagement") {
            @Override
            public void init() {
                register(uxModule.scaffold(this,AdminGroupResource.class));
                register(uxModule.scaffold(this,UserResource.class));
            }
        });

        addModule(new Module("Settings") {
            @Override
            public void init() {
                register(uxModule.scaffold(this,PropertyResource.class));
                Resource scaffold = uxModule.scaffold(this,PropertyValueResource.class);
                //scaffold.getResourceUx()
                //        .addView("setController", staticView(classpath("myController.js")));
                register(scaffold);


                AngularResourceUX ux = new AngularResourceUX(staticView(classpath("/org/github/mrconfig/ux/propertyimport/service.js")));
                Resource resource = new Resource(PropertyImport.class, PropertyImportResource.class);
                register(resource);


                addResourceClass(PropertiesImportResource.class);
                addResourceClass(FileResource.class);
            }
        });


        //addResourceClass(JaxbProvider.class);
        //addResourceClass(JaxbProvider.class);
        addResourceClass(MultiPartFeature.class);
        addResourceClass(SessionInViewInterceptor.class);
        addResourceClass(RolesResources.class);

        //addResourceClass(BasicAuthFilter.class);


    }
}
