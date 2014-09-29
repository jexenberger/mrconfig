package org.github.levelthree.angular;

import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import static org.github.levelthree.util.IOUtil.pwd;

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
                CONFIG.setDirectoryForTemplateLoading(new File(pwd()+"/src/main/resources/org/github/levelthree/ux/form"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return CONFIG;
    }

}
