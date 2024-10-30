package com.teamcubation.reportservice.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CurlGenerator {

    public static String generateCurl(String url, String method, String contentType, JsonConvertible data) {
        StringBuilder curl = new StringBuilder();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonData = gson.toJson(data);
        curl.append("\n");
        curl.append("curl -X ").append(method).append(" \\\n");
        curl.append("'http://localhost:8000").append(url).append("' \\\n");
        curl.append("--header 'Content-Type: ").append(contentType).append("' \\\n");
        curl.append("--data-raw '").append(jsonData).append("'");

        return curl.toString();
    }
}

