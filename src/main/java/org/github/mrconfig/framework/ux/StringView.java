package org.github.mrconfig.framework.ux;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;

/**
 * Created by julian3 on 2014/08/15.
 */
public class StringView implements View{

    private String content;

    public StringView(String content) {
        Objects.requireNonNull(content,"content is required");
        this.content = content;
    }

    @Override
    public void render(Map<String, Object> model, OutputStream target) {
        try {
            target.write(content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
