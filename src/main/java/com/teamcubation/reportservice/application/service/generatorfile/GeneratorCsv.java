package com.teamcubation.reportservice.application.service.generatorfile;
import com.opencsv.CSVWriter;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.BonoDto;
import com.teamcubation.reportservice.infrastructure.adapter.out.externalapi.dto.StockDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class GeneratorCsv {

    public static byte[] generateCsv(List<BonoDto> bonds, List<StockDto> stocks) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVWriter writer = new CSVWriter(new OutputStreamWriter(out));

        if (bonds != null) {
            writer.writeAll(generateCsvBonds(bonds));
        }
        if (stocks != null) {
            writer.writeAll(generateCsvStocks(stocks));
        }

        writer.close();
        return out.toByteArray();
    }

    private static List<String[]> generateCsvBonds(List<BonoDto> bonds) {
        List<String[]> bondsCsv = new ArrayList<>();
        String[] header = {"ID", "Name Bond", "Price", "Interest Rate", "Creation Date"};
        bondsCsv.add(header);
        for (BonoDto bonoDto : bonds) {
            bondsCsv.add(new String[]{String.valueOf(
                    bonoDto.getId()),
                    bonoDto.getName(),
                    String.valueOf(bonoDto.getPrice()),
                    String.valueOf(bonoDto.getInterestRate()),
                    bonoDto.getCreationDate().toString()});
        }

        return bondsCsv;
    }

    private static List<String[]> generateCsvStocks(List<StockDto> stocks) {
        List<String[]> stocksCsv = new ArrayList<>();
        String[] header = {"ID", "Name Stock", "Price", "Dividend", "Creation Date"};
        stocksCsv.add(header);
        for (StockDto stockDto : stocks) {
            stocksCsv.add(new String[]{String.valueOf(
                    stockDto.getId()),
                    stockDto.getName(),
                    String.valueOf(stockDto.getPrice()),
                    String.valueOf(stockDto.getDividend()),
                    stockDto.getCreationDate().toString()});
        }

        return stocksCsv;
    }


}
