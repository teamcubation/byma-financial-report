package com.teamcubation.reportservice.application.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;
import org.springframework.stereotype.Component;

import java.awt.*;
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

        com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 16, com.lowagie.text.Font.BOLD, Color.BLACK);
        com.lowagie.text.Font tableHeaderFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 12, com.lowagie.text.Font.BOLD, Color.WHITE);
        com.lowagie.text.Font tableBodyFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 10, com.lowagie.text.Font.NORMAL, Color.BLACK);

        Paragraph titleBonds = new Paragraph("Lista de Bonos", titleFont);
        titleBonds.setAlignment(Element.ALIGN_CENTER);
        titleBonds.setSpacingAfter(10);
        document.add(titleBonds);

        PdfPTable bondTable = new PdfPTable(4);
        bondTable.setWidthPercentage(100);

        addTableHeader(bondTable, "ID", "Nombre", "Precio", "Tasa de Interés", tableHeaderFont);

        for (BonoDto bond : bonds) {
            bondTable.addCell(new PdfPCell(new Phrase(String.valueOf(bond.getId()), tableBodyFont)));
            bondTable.addCell(new PdfPCell(new Phrase(bond.getName(), tableBodyFont)));
            bondTable.addCell(new PdfPCell(new Phrase(String.valueOf(bond.getPrice()), tableBodyFont)));
            bondTable.addCell(new PdfPCell(new Phrase(String.valueOf(bond.getInterestRate()) + "%", tableBodyFont)));
        }

        document.add(bondTable);

        document.add(new Paragraph("\n"));

        Paragraph titleStocks = new Paragraph("Lista de Acciones", titleFont);
        titleStocks.setAlignment(Element.ALIGN_CENTER);
        titleStocks.setSpacingAfter(10);
        document.add(titleStocks);

        PdfPTable stockTable = new PdfPTable(4);
        stockTable.setWidthPercentage(100);

        addTableHeader(stockTable, "ID", "Nombre", "Precio", "Dividendo (%)", tableHeaderFont);

        for (StockDto stock : stocks) {
            stockTable.addCell(new PdfPCell(new Phrase(String.valueOf(stock.getId()), tableBodyFont)));
            stockTable.addCell(new PdfPCell(new Phrase(stock.getName(), tableBodyFont)));
            stockTable.addCell(new PdfPCell(new Phrase(String.valueOf(stock.getPrice()), tableBodyFont)));
            stockTable.addCell(new PdfPCell(new Phrase(String.valueOf(stock.getDividend()) + "%", tableBodyFont)));
        }

        document.add(stockTable);

        document.close();

        return baos.toByteArray();
    }

    private static void addTableHeader(PdfPTable table, String col1, String col2, String col3, String col4, com.lowagie.text.Font headerFont) {
        PdfPCell header1 = new PdfPCell(new Phrase(col1, headerFont));
        header1.setBackgroundColor(Color.GRAY);
        header1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header1);

        PdfPCell header2 = new PdfPCell(new Phrase(col2, headerFont));
        header2.setBackgroundColor(Color.GRAY);
        header2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header2);

        PdfPCell header3 = new PdfPCell(new Phrase(col3, headerFont));
        header3.setBackgroundColor(Color.GRAY);
        header3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header3);

        PdfPCell header4 = new PdfPCell(new Phrase(col4, headerFont));
        header4.setBackgroundColor(Color.GRAY);
        header4.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header4);
    }
}