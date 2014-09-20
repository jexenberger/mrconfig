package org.github.levelthree.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.github.levelthree.util.IOUtil.pwd;

/**
 * Created by julian3 on 2014/07/20.
 */
@Path("/")
public class StaticResource {



    public static void setDebugPath(String path) {
       PATH = path;
    }

    public static void setRelativeDebugPath(String path) {
       PATH = pwd()+path;
    }


    private static String PATH = null;

    public static Map<String, String> MIME_TYPES;

    static {
        MIME_TYPES = new HashMap<>();
        MIME_TYPES.put("js","application/javascript");
        MIME_TYPES.put("css","text/css");
        MIME_TYPES.put("ttf","font/truetype");
        MIME_TYPES.put("woff","application/font-woff");
    }


    @GET
    @Produces({MediaType.TEXT_HTML, "application/javascript", MediaType.TEXT_PLAIN})
    @Path("{subResources: [a-zA-Z0-9_/\\.\\-]+}")
    public Response get(@PathParam("subResources") String resource) {
        System.out.println(resource);
        StreamingOutput result = (output) -> {
            try {
                System.out.println(pwd());
                FileInputStream fos = new FileInputStream(PATH + "/" + resource);
                int read = fos.read();
                while (read != -1) {
                    output.write(read);
                    read = fos.read();
                }
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        };


        String type = MediaType.TEXT_HTML;

        String[] parts = resource.split("\\.");
        String extension = parts[parts.length-1];
        if (MIME_TYPES.containsKey(extension)) {
            type = MIME_TYPES.get(extension);
        }


        return Response.ok(result).type(type).build();
    }


}
