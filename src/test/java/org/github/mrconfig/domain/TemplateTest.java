package org.github.mrconfig.domain;

import org.github.mrconfig.service.BaseJPA;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;

import static java.util.stream.Collectors.joining;
import static org.github.mrconfig.domain.Property.importProperties;

/**
 * Created by julian3 on 2014/07/19.
 */
public class TemplateTest extends BaseJPA{


    @Test
    public void testGenerate() throws Exception {

        EnvironmentGroup group = new EnvironmentGroup("parent",null).save();
        Server server = new Server("test",group,"test","qweqwe","linux","test","test").save();

        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/test.ftl")));

        Template t = new Template("testTemplate",reader.lines().collect(joining("\n")).getBytes()).save();

        importProperties(group, getClass().getResourceAsStream("/parent.properties"));
        importProperties(server, getClass().getResourceAsStream("/test.properties"));

        StringWriter target = new StringWriter();
        t.generate(server, target);

        System.out.println(target.toString());



    }
}
