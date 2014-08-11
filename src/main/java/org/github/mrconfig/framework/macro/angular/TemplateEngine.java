package org.github.mrconfig.framework.macro.angular;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static org.github.mrconfig.framework.util.IOUtil.pwd;

/**
 * Created by julian3 on 2014/07/20.
 */
public class TemplateEngine {

    private static Configuration CONFIG;


    public static Configuration getConfiguration() {
        if (CONFIG == null) {
            CONFIG = new Configuration();
            CONFIG.setLocale(Locale.getDefault());
            //CONFIG.setClassForTemplateLoading(TemplateEngine.class, "templates");
            System.out.println(pwd());
            try {
                CONFIG.setDirectoryForTemplateLoading(new File(pwd()+"/src/main/resources/org/github/mrconfig/framework/macro/angular/templates"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return CONFIG;
    }

}
