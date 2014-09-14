package org.github.mrconfig;

import org.github.mrconfig.domain.*;
import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.activerecord.ProviderFactory;
import org.github.mrconfig.framework.activerecord.jpa.JPAModule;
import org.github.mrconfig.framework.resources.JaxbProvider;
import org.github.mrconfig.framework.resources.RolesResources;
import org.github.mrconfig.framework.resources.SessionInViewInterceptor;
import org.github.mrconfig.framework.ux.form.BeanFormBuilder;
import org.github.mrconfig.framework.ux.form.DefaultUXModule;
import org.github.mrconfig.framework.ux.form.Form;
import org.github.mrconfig.framework.ux.form.FormBuilder;
import org.github.mrconfig.resources.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.github.mrconfig.domain.Property.importProperties;
import static org.github.mrconfig.framework.ux.ClasspathSource.classpath;
import static org.github.mrconfig.framework.ux.StaticView.staticView;

/**
 * Created by w1428134 on 2014/07/16.
 */
public class MrConfigApplication extends ResourceConfig {

    public MrConfigApplication(Class<?>... classes) {
        super(classes);
    }


    public static void main(String[] args) throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {


                Module myModule = new Module("Main") {

                    @Override
                    public void init() {


                        this.addModule(new JPAModule("org.github.mrconfig.domain"));
                        this.addModule(new DefaultUXModule());

                        register(Resource.scaffold(EnvironmentResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(DataCentreResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(EnvironmentGroupResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(ServerResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(PropertyResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(AdminGroupResource.class, BeanFormBuilder::form));
                        register(Resource.scaffold(UserResource.class, (resource) -> {
                            Form roleForm = BeanFormBuilder.formBuilder(resource, (builder) -> builder.addCollection("roles", BeanFormBuilder.fromClass(new FormBuilder("roles", "Roles"), RoleMapping.class).getForm()).getForm()).create().getForm();
                            return roleForm;
                        }));
                        Resource scaffold = Resource.scaffold(PropertyValueResource.class, BeanFormBuilder::form);
                        scaffold.getUxModule()
                                .addView("controller", staticView(classpath("myController.js")));
                        register(scaffold);

                        addResourceClass(PropertiesImportResource.class);
                        addResourceClass(FileResource.class);

                        addResourceClass(JaxbProvider.class);
                        addResourceClass(JaxbProvider.class);
                        addResourceClass(MultiPartFeature.class);
                        addResourceClass(SessionInViewInterceptor.class);
                        addResourceClass(RolesResources.class);
                        //addResourceClass(BasicAuthFilter.class);
                    }
                };


                ResourceConfig rc = new MrConfigApplication(myModule.allResources());


                Map<String, Object> properties = new HashMap<>();
                properties.put(ServerProperties.TRACING, "ALL");
                properties.put(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
                rc.setProperties(properties);
                //rc.register(provider);
                HttpServer server = null;
                try {
                    server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:6001"), rc, true);
                    //StaticHttpHandler httpHandler = new StaticHttpHandler("/Users/julian3/MrConfig/src/main/webapp");
                    //server.getServerConfiguration().addHttpHandler(httpHandler);
                    server.getServerConfiguration().setTraceEnabled(true);
                    bootstrap();
                    while (0 == 0) {
                        Thread.yield();
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } finally {
                    if (server != null) {
                        server.shutdownNow();
                    }
                }


                //RuntimeDelegate instance = RuntimeDelegate.getInstance();
                //HttpHandler endpoint = instance.createEndpoint(new MrConfigApplication(), HttpHandler.class);
                //endpoint.

            }
        }).run();
    }

    public static void bootstrap() {


        User adminUser = new User("admin", "admin").save();
        User julian = new User("julian", "password").save();
        AdminGroup mainAdminGroup = new AdminGroup("all", "Main admin group", adminUser, julian).save();

        EnvironmentGroup root = new EnvironmentGroup("VitalityInternational", "VitalityInternational", null, mainAdminGroup).save();
        createDiscovery(root);
        createPru(root);
        createPingAn(root);
        createAIA(root);


        ProviderFactory.getProvider().clear();

    }

    public static void createDiscovery(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup disc = new EnvironmentGroup("Discovery", "Discovery", root, root.getOwner()).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("DiscoveryDevelopment", "DiscoveryDevelopment", disc, root.getOwner()).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01", "dhdvitintwl01", dev, "dhdvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup test = new EnvironmentGroup("DiscoveryTest", "DiscoveryTest", disc, root.getOwner()).save();
        Server dhtvitintwl01 = new Server("dhtvitintwl01", "dhtvitintwl01", test, "dhtvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup prod = new EnvironmentGroup("DiscoveryProduction", "DiscoveryProduction", disc, root.getOwner()).save();
        Server dhpvitintwl01 = new Server("dhpvitintwl01", "dhpvitintwl01", prod, "dhpvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server dhpvitintwl02 = new Server("dhpvitintwl02", "dhpvitintwl02", prod, "dhpvitintwl02.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        importProperties(disc, MrConfigApplication.class.getResourceAsStream("/parent.properties"));
        importProperties(prod, MrConfigApplication.class.getResourceAsStream("/test.properties"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(MrConfigApplication.class.getResourceAsStream("/org/github/mrconfig/test.ftl")));

        Template t = new Template("testTemplate", reader.lines().collect(joining("\n")).getBytes()).save();

    }

    public static void createPru(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup pru = new EnvironmentGroup("PruVitality", "PruVitality", root, root.getOwner()).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("PruVitalityDevelopment", "PruVitalityDevelopment", pru, root.getOwner()).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_pru", "dhdvitintwl01_pru", dev, "dhdvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup test = new EnvironmentGroup("PruVitalityTest", "PruVitalityTest", pru, root.getOwner()).save();
        Server dhtvitintwl01 = new Server("phtvmim02", "phtvmim02", test, "phtvmim02.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup prod = new EnvironmentGroup("PruVitalityProduction", "PruVitalityProduction", pru, root.getOwner()).save();
        Server dhpvitintwl01 = new Server("phpvi01", "phpvi01", prod, "phpvi01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server dhpvitintwl02 = new Server("phpvi02", "phpvi02", prod, "phpvi02.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();
    }

    public static void createPingAn(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup environment = new EnvironmentGroup("PingAnVitality", "PingAnVitality", root, root.getOwner()).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("PingAnVitalityDevelopment", "PingAnVitalityDevelopment", environment, root.getOwner()).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_pa", "dhdvitintwl01_pa", dev, "dhdvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup integration = new EnvironmentGroup("PingAnVitalityIntegration", "PingAnVitalityIntegration", environment, root.getOwner()).save();
        Server dhtvitintwl01 = new Server("paivitint01", "paivitint01", integration, "paivitint01", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup stage = new EnvironmentGroup("PingAnVitalityStaging", "PingAnVitalityStaging", environment, root.getOwner()).save();
        Server pasvitint01 = new Server("pasvitint01", "pasvitint01", stage, "pasvitint01", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server pasvitint02 = new Server("pasvitint02", "pasvitint02", stage, "pasvitint02", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup prod = new EnvironmentGroup("PingAnVitalityProduction", "PingAnVitalityProduction", environment, root.getOwner()).save();
        Server papvitint01 = new Server("papvitint01", "papvitint01", prod, "papvitint01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server papvitint02 = new Server("papvitint02", "papvitint02", prod, "papvitint02.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();
    }

    public static void createAIA(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup environment = new EnvironmentGroup("AIAVitality", "AIAVitality", root, root.getOwner()).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("AIAVitalityDevelopment", "AIAVitalityDevelopment", environment, root.getOwner()).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_aia", "dhdvitintwl01_aia", dev, "dhdvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup test = new EnvironmentGroup("AIAVitalityTest", "AIAVitalityTest", environment, root.getOwner()).save();
        Server dhtvitintwl01 = new Server("avtvitint02", "avtvitint02", test, "avpvitint01", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup qa = new EnvironmentGroup("AIAVitalityQA", "AIAVitalityQA", environment, root.getOwner()).save();
        Server pasvitint01 = new Server("avqvitint01", "avqvitint01", qa, "avqvitint01", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server pasvitint02 = new Server("avqvitint02", "avqvitint02", qa, "avqvitint02", "127.0.0.1", "linux", null, null, root.getOwner()).save();

        EnvironmentGroup prod = new EnvironmentGroup("AIAVitalityProduction", "AIAVitalityProduction", environment, root.getOwner()).save();
        Server papvitint01 = new Server("avpvitint01", "avpvitint01", prod, "avpvitint01", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server papvitint02 = new Server("avpvitint02", "avpvitint02", prod, "avpvitint02", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server papvitint03 = new Server("avpvitint03", "avpvitint03", prod, "avpvitint03", "127.0.0.1", "linux", null, null, root.getOwner()).save();
        Server papvitint04 = new Server("avpvitint04", "avpvitint04", prod, "avpvitint04", "127.0.0.1", "linux", null, null, root.getOwner()).save();
    }

}
