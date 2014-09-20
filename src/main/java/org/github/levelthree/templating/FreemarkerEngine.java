package org.github.levelthree.templating;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import org.github.mrconfig.domain.Template;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.github.levelthree.activerecord.ActiveRecord.findById;

/**
 * Created by julian3 on 2014/07/19.
 */
public class FreemarkerEngine {

    private static Configuration CONFIG;


    public static Configuration getConfiguration() {
        if (CONFIG == null) {
            CONFIG = new Configuration();
            CONFIG.setLocale(Locale.getDefault());
            CONFIG.setTemplateLoader(new TemplateLoader() {
                @Override
                public Object findTemplateSource(String s) throws IOException {
                    s = removeI18N(s);
                    Optional<Template> template = findById(Template.class,s);
                    return template.get();
                }

                private String removeI18N(String s) {
                    Locale locale = Locale.getDefault();
                    String language = locale.getLanguage()+"_"+locale.getCountry();
                    if (s.endsWith(language)) {
                        s = s.substring(0,s.length()-language.length()-1);
                    }
                    return s;
                }

                @Override
                public long getLastModified(Object o) {
                    Optional<Template> template = findById(Template.class, ((Template) o).getId());
                    return template.get().getLastModified().getTime();
                }

                @Override
                public Reader getReader(Object o, String s) throws IOException {
                    StringReader stringReader = new StringReader(new String(((Template) o).getContent()));
                    return stringReader;
                }

                @Override
                public void closeTemplateSource(Object o) throws IOException {
                    //nothing to do
                }
            });
        }
        return CONFIG;
    }


    public  static void generate(Map<String, Object> model, String templateKey, Writer target) {
        try {
            freemarker.template.Template source = getConfiguration().getTemplate(templateKey);
            source.process(model, target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
