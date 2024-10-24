package com.teamcubation.reportservice.application.service.generatorfile;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;


@Component
public class GeneratorPdf {

    public static byte[] generatePdfContent(List<BonoDto> bonds, List<StockDto> stocks) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);

        document.open();
        if (bonds != null) {
            document.add(new Paragraph("Bonos"));
            document.add(new Paragraph(bonds.toString()));
        }
        if (stocks != null) {
            document.add(new Paragraph("Acciones"));
            document.add(new Paragraph(stocks.toString()));
        }
        document.close();

        return baos.toByteArray();
    }
}

