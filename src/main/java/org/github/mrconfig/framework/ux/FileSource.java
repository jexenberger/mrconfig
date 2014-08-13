package org.github.mrconfig.framework.ux;

import java.io.*;

import static java.util.Objects.requireNonNull;

/**
 * Created by julian3 on 2014/08/10.
 */
public class FileSource implements Source {


    File input;

    public FileSource(File input) {
        requireNonNull(input,"input file cannot be null");
        assert input.exists();
        this.input = input;
    }

    @Override
    public InputStream getSource()  {
        try {
            return new FileInputStream(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPath() {
        return input.getName();
    }


    public File getInput() {
        return input;
    }

    public static Source file(File file) {
        return new FileSource(file);
    }
}
