package org.github.mrconfig;


import org.github.levelthree.LevelThreeApplication;
import org.github.mrconfig.domain.*;
import org.github.levelthree.activerecord.ProviderFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static org.github.mrconfig.domain.Property.importProperties;
import static org.github.levelthree.ux.ClasspathSource.classpath;

/**
 * Created by w1428134 on 2014/07/16.
 */
public class MrConfigApplication extends LevelThreeApplication {





    public static void main(String[] args) throws Exception {

        new MrConfigApplication().start(true);
    }

    @Override
    protected void init() {
        add(new Main());
    }

    public void bootstrap() {


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
