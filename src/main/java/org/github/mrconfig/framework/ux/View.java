package org.github.mrconfig.framework.ux;

import java.io.*;

/**
 * Created by julian3 on 2014/08/10.
 */
public interface View {


    InputStream getSource();

    String getPath();


    default void render(OutputStream outputStream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(getSource());
            byte[] chunk = new byte[1024];
            int read =  0;
            while ((read = bis.read(chunk)) != -1) {
                if (read < 1024) {
                    byte[]  readBytes = new byte[read];
                    System.arraycopy(chunk,0,readBytes,0,read);
                    outputStream.write(readBytes);
                } else {
                    outputStream.write(chunk);
                }
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
