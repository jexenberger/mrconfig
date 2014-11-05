package org.github.mrconfig.resources;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.github.levelthree.resources.BaseCRUDResource;
import org.github.mrconfig.domain.Server;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.util.Collection;

import static java.lang.String.join;

/**
 * Created by julian3 on 2014/07/18.
 */
@Path("/servers")
public class ServerResource extends BaseCRUDResource<Server, String> {


    @Override
    public StreamingOutput resolveCustomerHandler(MediaType mediaType, Collection<Server> results) {
        if (mediaType.getSubtype().endsWith("csv")) {
            return (stream) -> {
                stream.write("Name,DNS,IP Address,OS\n".getBytes());
                for (Server result : results) {
                    stream.write(join(",",result.getName(), result.getDnsName(), result.getIpAddress(), result.getOperatingSystem(),"\n").getBytes());
                }
            };
        }
        return null;
    }
}
