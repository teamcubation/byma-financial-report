package com.teamcubation.reportservice.application.service.generatorfile;

import com.lowagie.text.Font;
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

    private static final String BONDS_TITLE = "Lista de Bonos";
    private static final String STOCKS_TITLE = "Lista de Acciones";
    private static final int TITLE_FONT_SIZE = 16;
    private static final int HEADER_FONT_SIZE = 12;
    private static final int BODY_FONT_SIZE = 10;
    private static final Color TITLE_COLOR = Color.BLACK;
    private static final Color HEADER_COLOR = Color.GRAY;
    private static final Color HEADER_TEXT_COLOR = Color.WHITE;
    private static final Color BODY_TEXT_COLOR = Color.BLACK;
    private static final int TABLE_COLUMNS = 4;
    private static final float SPACING_AFTER_TITLE = 10f;
    private static final float TABLE_WIDTH_PERCENTAGE = 100f;

    private static final Font TITLE_FONT = new Font(Font.HELVETICA, TITLE_FONT_SIZE, Font.BOLD, TITLE_COLOR);
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, HEADER_FONT_SIZE, Font.BOLD, HEADER_TEXT_COLOR);
    private static final Font BODY_FONT = new Font(Font.HELVETICA, BODY_FONT_SIZE, Font.NORMAL, BODY_TEXT_COLOR);

    public static byte[] generatePdfContent(List<BonoDto> bonds, List<StockDto> stocks) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);

        document.open();

        addTitle(document, BONDS_TITLE);
        addBondTable(document, bonds);

        document.add(new Paragraph("\n"));

        addTitle(document, STOCKS_TITLE);
        addStockTable(document, stocks);

        document.close();
        return baos.toByteArray();
    }

    private static void addTitle(Document document, String titleText) throws DocumentException {
        Paragraph title = new Paragraph(titleText, TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(SPACING_AFTER_TITLE);
        document.add(title);
    }

    private static void addBondTable(Document document, List<BonoDto> bonds) throws DocumentException {
        if (bonds == null || bonds.isEmpty()) return;

        PdfPTable bondTable = new PdfPTable(TABLE_COLUMNS);
        bondTable.setWidthPercentage(TABLE_WIDTH_PERCENTAGE);

        addTableHeader(bondTable, "ID", "Nombre", "Precio", "Tasa de Interés");

        for (BonoDto bond : bonds) {
            bondTable.addCell(new PdfPCell(new Phrase(String.valueOf(bond.getId()), BODY_FONT)));
            bondTable.addCell(new PdfPCell(new Phrase(bond.getName(), BODY_FONT)));
            bondTable.addCell(new PdfPCell(new Phrase(String.valueOf(bond.getPrice()), BODY_FONT)));
            bondTable.addCell(new PdfPCell(new Phrase(bond.getInterestRate() + "%", BODY_FONT)));
        }

        document.add(bondTable);
    }

    private static void addStockTable(Document document, List<StockDto> stocks) throws DocumentException {
        if (stocks == null || stocks.isEmpty()) return;

        PdfPTable stockTable = new PdfPTable(TABLE_COLUMNS);
        stockTable.setWidthPercentage(TABLE_WIDTH_PERCENTAGE);

        addTableHeader(stockTable, "ID", "Nombre", "Precio", "Dividendo (%)");

        for (StockDto stock : stocks) {
            stockTable.addCell(new PdfPCell(new Phrase(String.valueOf(stock.getId()), BODY_FONT)));
            stockTable.addCell(new PdfPCell(new Phrase(stock.getName(), BODY_FONT)));
            stockTable.addCell(new PdfPCell(new Phrase(String.valueOf(stock.getPrice()), BODY_FONT)));
            stockTable.addCell(new PdfPCell(new Phrase(stock.getDividend() + "%", BODY_FONT)));
        }

        document.add(stockTable);
    }

    private static void addTableHeader(PdfPTable table, String column1Title, String column2Title, String column3Title, String column4Title) {
        addHeaderCell(table, column1Title);
        addHeaderCell(table, column2Title);
        addHeaderCell(table, column3Title);
        addHeaderCell(table, column4Title);
    }

    private static void addHeaderCell(PdfPTable table, String columnTitle) {
        PdfPCell header = new PdfPCell(new Phrase(columnTitle, HEADER_FONT));
        header.setBackgroundColor(HEADER_COLOR);
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
}
