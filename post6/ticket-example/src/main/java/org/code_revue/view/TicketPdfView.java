package org.code_revue.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.*;
import org.code_revue.domain.Ticket;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Implementation of Spring View that generates a PDF for {@link org.code_revue.domain.Ticket} objects.
 * @author Mike Fanning
 */
public class TicketPdfView implements View {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MMM d yyyy HH:mm:ss");

    @Override
    public String getContentType() {
        return "application/pdf";
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Ticket t = (Ticket) model.get("ticket");
        response.setHeader("Content-Disposition", "inline; filename=" + t.getTicketId() + ".pdf");

        // iText Document setup
        Document document = new Document(PageSize.A6.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        PdfContentByte contentByte = writer.getDirectContent();

        // Set up barcode information
        Barcode128 barcode = new Barcode128();
        barcode.setCode(Ticket.BARCODE_PREFIX + t.getTicketId());
        barcode.setAltText("");
        barcode.setBarHeight(40.0f);

        // Add a table with the barcode, ticket-holder name and creation date
        PdfPTable table = new PdfPTable(1);

        PdfPCell bCodeCell = new PdfPCell(barcode.createImageWithBarcode(contentByte, null, null));
        bCodeCell.setBorder(PdfPCell.NO_BORDER);
        bCodeCell.setFixedHeight(50.0f);
        table.addCell(bCodeCell);

        PdfPCell nameCell = new PdfPCell(new Phrase(t.getTicketholderName()));
        nameCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(nameCell);

        PdfPCell dateCell = new PdfPCell(new Phrase(DATE_FORMAT.format(t.getCreatedDate())));
        dateCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(dateCell);

        document.add(table);

        document.close();
    }
}
