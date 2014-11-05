package org.github.mrconfig.resources;

import org.github.mrconfig.domain.Server;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import static java.util.Arrays.asList;

/**
 * Created by w1428134 on 2014/11/05.
 */
public class ServerListReportTest {


    @Test
    public void testGenerate() throws Exception {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ServerListReport.generate(asList(new Server("dhpvitintwl01", "dhpvitintwl01", null, "dhpvitintwl01.discsrv.co.za", "127.0.0.1", "linux", null, null, null)), new FileOutputStream("out.pdf"));
        System.out.println(output.toString());

    }
}
