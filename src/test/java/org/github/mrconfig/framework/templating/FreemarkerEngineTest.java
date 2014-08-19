package org.github.mrconfig.framework.templating;

import org.github.mrconfig.domain.Template;
import org.github.mrconfig.service.BaseDomainJPATest;
import org.github.mrconfig.service.BaseJPA;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FreemarkerEngineTest extends BaseDomainJPATest{

    public static final String TEST_TEMPLATE = "test_template";

    @Test
    public void testGenerate() throws Exception {

        Template t = new Template();
        t.setId(TEST_TEMPLATE);
        t.setContent("${name}".getBytes());
        t.save();

        HashMap<String, Object> model = new HashMap<>();
        model.put("name","Jack");
        StringWriter stringWriter = new StringWriter();
        FreemarkerEngine.generate(model, TEST_TEMPLATE, stringWriter);

        System.out.println(stringWriter.toString());

    }
}
