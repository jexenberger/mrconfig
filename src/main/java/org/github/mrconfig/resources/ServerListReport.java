package org.github.mrconfig.resources;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.github.mrconfig.domain.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by w1428134 on 2014/11/05.
 */
public class ServerListReport {

    public static void generate(List<Server> servers, OutputStream output) {
        try {
            PDDocument document = new PDDocument();
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);


            // Start a new content stream which will "hold" the to be created content
            PDFont font = PDType1Font.HELVETICA_BOLD;
            PDPageContentStream contentStream = new PDPageContentStream(document, blankPage);
            writeText(font, contentStream, 100, 700, "Servers");
            writeText(font, contentStream, 100, 800, "Name");
            writeText(font, contentStream, 100, 800, "DNS");
            writeText(font, contentStream, 100, 800, "IP");
            writeText(font, contentStream, 100, 800, "OS");

// Make sure that the content stream is closed:
            contentStream.close();


            document.save(output);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeText(PDFont font, PDPageContentStream contentStream, int x, int y, String text) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(x, y);
        contentStream.drawString(text);
        contentStream.endText();
    }

}
