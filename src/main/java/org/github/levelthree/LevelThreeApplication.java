package org.github.levelthree;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.core.Application;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by w1428134 on 2014/11/04.
 */
public abstract class LevelThreeApplication extends ResourceConfig {


    Set<Module> modules;

    public LevelThreeApplication() {
        modules = new LinkedHashSet<>();
    }


    public LevelThreeApplication(Class<?>... classes) {
        super(classes);
        modules = new LinkedHashSet<>();
    }

    public LevelThreeApplication add(Module module) {
        this.modules.add(module);
        return this;
    }



    protected abstract void init();


    protected void loadModules() {
        for (Module module : modules) {
            Arrays.stream(module.allResources()).forEach(this::register);
        }
    }


    public  void start(final boolean debugging) {
        final LevelThreeApplication app = this;
        app.init();
        app.loadModules();
        new Thread(() -> {


            Map<String, Object> properties = new HashMap<>();
            if (debugging) {
                properties.put(ServerProperties.TRACING, "ALL");
                properties.put(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
            }
            app.setProperties(properties);
            //rc.register(provider);
            HttpServer server = null;
            try {
                System.out.println("bootstrapping");
                app.bootstrap();
                server = GrizzlyHttpServerFactory.createHttpServer(new URI("http://localhost:6001"), app, true);
                if (debugging) {
                    server.getServerConfiguration().setTraceEnabled(debugging);
                }
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
        }).start();
    }

    protected void bootstrap() {

    }
}
