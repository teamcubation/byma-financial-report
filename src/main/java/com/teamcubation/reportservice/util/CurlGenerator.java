package com.teamcubation.reportservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class CurlGenerator {

    private static final String GET = "GET";

    public static String generateCurl(String url, String method, String contentType, Object data) {
        StringBuilder curl = new StringBuilder();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String queryParams = "";
        if (GET.equalsIgnoreCase(method) && data instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) data;
            StringBuilder queryBuilder = new StringBuilder();
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                if (entry.getValue() != null) {
                    if (queryBuilder.length() > 0) {
                        queryBuilder.append("&");
                    }
                    queryBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                }
            }
            queryParams = "?" + queryBuilder.toString();
        }

        curl.append("\n");
        curl.append("curl -X ").append(method).append(" \\\n");
        curl.append("'http://localhost:8000").append(url).append(queryParams).append("' \\\n");
        curl.append("--header 'Content-Type: ").append(contentType).append("'");

        // Solo agregar `--data-raw` si no es un GET
        if (!GET.equalsIgnoreCase(method) && data != null) {
            String jsonData = gson.toJson(data);
            curl.append(" \\\n--data-raw '").append(jsonData).append("'");
        }

        return curl.toString();
    }
}

