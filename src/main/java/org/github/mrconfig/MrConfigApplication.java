package org.github.mrconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.github.mrconfig.domain.*;
import org.github.mrconfig.framework.Module;
import org.github.mrconfig.framework.Resource;
import org.github.mrconfig.framework.ResourceRegistry;
import org.github.mrconfig.framework.activerecord.jpa.JPAModule;
import org.github.mrconfig.framework.activerecord.jpa.JPAProvider;
import org.github.mrconfig.framework.activerecord.ProviderFactory;
import org.github.mrconfig.framework.resources.GenerateExceptionMapper;
import org.github.mrconfig.framework.resources.JaxbProvider;
import org.github.mrconfig.framework.resources.MenuResource;
import org.github.mrconfig.framework.macro.FormRegistry;
import org.github.mrconfig.framework.macro.FormResource;
import org.github.mrconfig.framework.macro.StaticResource;
import org.github.mrconfig.framework.ux.form.BeanFormBuilder;
import org.github.mrconfig.framework.ux.form.DefaultUXModule;
import org.github.mrconfig.resources.TemplateResource;
import org.github.mrconfig.resources.*;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
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

/**
 * Created by w1428134 on 2014/07/16.
 */
public class MrConfigApplication extends ResourceConfig {

    public MrConfigApplication(Class<?>... classes) {
        super(classes);
    }




    public static void main(String[] args) throws Exception{

        new Thread(new Runnable() {
            @Override
            public void run() {


                Module myModule = new Module() {

                    @Override
                    public void init() {


                        this.addModule(new JPAModule("org.github.mrconfig.domain"));
                        this.addModule(new DefaultUXModule());

                        Resource.scaffold(EnvironmentResource.class, BeanFormBuilder::form);
                        Resource.scaffold(EnvironmentGroupResource.class,BeanFormBuilder::form);
                        Resource.scaffold(ServerResource.class,BeanFormBuilder::form);
                        Resource.scaffold(PropertyResource.class,BeanFormBuilder::form);
                        Resource.scaffold(AdminGroupResource.class,BeanFormBuilder::form);
                        Resource.scaffold(UserResource.class,BeanFormBuilder::form);
                        Resource.scaffold(ManeeshResource.class,BeanFormBuilder::form);
                        Resource.scaffold(PropertyValueResource.class,BeanFormBuilder::form);
                        Resource.scaffold(TemplateResource.class,BeanFormBuilder::form);

                        addResourceClass(PropertiesImportResource.class);
                        addResourceClass(FileResource.class);

                        addResourceClass(JaxbProvider.class);
                        addResourceClass(JaxbProvider.class);
                        addResourceClass(GenerateExceptionMapper.class);
                        addResourceClass(MultiPartFeature.class);
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
                    while (0==0) {
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

        EnvironmentGroup root = new EnvironmentGroup("VitalityInternational",null,mainAdminGroup).save();
        createDiscovery(root);
        createPru(root);
        createPingAn(root);
        createAIA(root);


        ProviderFactory.getProvider().clear();

    }

    public static void createDiscovery(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup disc = new EnvironmentGroup("Discovery", root).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("DiscoveryDevelopment", disc).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01", dev,"dhdvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup test = new EnvironmentGroup("DiscoveryTest", disc).save();
        Server dhtvitintwl01 = new Server("dhtvitintwl01", test,"dhtvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup prod = new EnvironmentGroup("DiscoveryProduction", disc).save();
        Server dhpvitintwl01 = new Server("dhpvitintwl01", prod,"dhpvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();
        Server dhpvitintwl02 = new Server("dhpvitintwl02", prod,"dhpvitintwl02.discsrv.co.za","127.0.0.1","linux",null,null).save();

        importProperties(disc, MrConfigApplication.class.getResourceAsStream("/parent.properties"));
        importProperties(prod, MrConfigApplication.class.getResourceAsStream("/test.properties"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(MrConfigApplication.class.getResourceAsStream("/org/github/mrconfig/test.ftl")));

        Template t = new Template("testTemplate",reader.lines().collect(joining("\n")).getBytes()).save();

    }

    public static void createPru(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup pru = new EnvironmentGroup("PruVitality", root).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("PruVitalityDevelopment", pru).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_pru", dev,"dhdvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup test = new EnvironmentGroup("PruVitalityTest", pru).save();
        Server dhtvitintwl01 = new Server("phtvmim02", test,"phtvmim02.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup prod = new EnvironmentGroup("PruVitalityProduction", pru).save();
        Server dhpvitintwl01 = new Server("phpvi01", prod,"phpvi01.discsrv.co.za","127.0.0.1","linux",null,null).save();
        Server dhpvitintwl02 = new Server("phpvi02", prod,"phpvi02.discsrv.co.za","127.0.0.1","linux",null,null).save();
    }

    public static void createPingAn(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup environment = new EnvironmentGroup("PingAnVitality", root).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("PingAnVitalityDevelopment", environment).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_pa", dev,"dhdvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup integration = new EnvironmentGroup("PingAnVitalityIntegration", environment).save();
        Server dhtvitintwl01 = new Server("paivitint01", integration,"paivitint01","127.0.0.1","linux",null,null).save();

        EnvironmentGroup stage = new EnvironmentGroup("PingAnVitalityStaging", environment).save();
        Server pasvitint01 = new Server("pasvitint01", stage,"pasvitint01","127.0.0.1","linux",null,null).save();
        Server pasvitint02 = new Server("pasvitint02", stage,"pasvitint02","127.0.0.1","linux",null,null).save();

        EnvironmentGroup prod = new EnvironmentGroup("PingAnVitalityProduction", environment).save();
        Server papvitint01 = new Server("papvitint01", prod,"papvitint01.discsrv.co.za","127.0.0.1","linux",null,null).save();
        Server papvitint02 = new Server("papvitint02", prod,"papvitint02.discsrv.co.za","127.0.0.1","linux",null,null).save();
    }

    public static void createAIA(EnvironmentGroup root) {
        //main environments
        EnvironmentGroup environment = new EnvironmentGroup("AIAVitality", root).save();
        //sub envs
        EnvironmentGroup dev = new EnvironmentGroup("AIAVitalityDevelopment", environment).save();
        Server dhdvitintwl01 = new Server("dhdvitintwl01_aia", dev,"dhdvitintwl01.discsrv.co.za","127.0.0.1","linux",null,null).save();

        EnvironmentGroup test = new EnvironmentGroup("AIAVitalityTest", environment).save();
        Server dhtvitintwl01 = new Server("avtvitint02", test,"avpvitint01","127.0.0.1","linux",null,null).save();

        EnvironmentGroup qa = new EnvironmentGroup("AIAVitalityQA", environment).save();
        Server pasvitint01 = new Server("avqvitint01", qa,"avqvitint01","127.0.0.1","linux",null,null).save();
        Server pasvitint02 = new Server("avqvitint02", qa,"avqvitint02","127.0.0.1","linux",null,null).save();

        EnvironmentGroup prod = new EnvironmentGroup("AIAVitalityProduction", environment).save();
        Server papvitint01 = new Server("avpvitint01", prod,"avpvitint01","127.0.0.1","linux",null,null).save();
        Server papvitint02 = new Server("avpvitint02", prod,"avpvitint02","127.0.0.1","linux",null,null).save();
        Server papvitint03 = new Server("avpvitint03", prod,"avpvitint03","127.0.0.1","linux",null,null).save();
        Server papvitint04 = new Server("avpvitint04", prod,"avpvitint04","127.0.0.1","linux",null,null).save();
    }

}
