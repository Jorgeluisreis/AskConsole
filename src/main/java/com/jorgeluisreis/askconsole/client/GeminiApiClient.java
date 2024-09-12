package com.jorgeluisreis.askconsole.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.jorgeluisreis.askconsole.util.JsonUtil;

public class GeminiApiClient {

    private static final String GEMINI_MODEL = "gemini-1.5-flash";
    private String apiKey;

    public GeminiApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isApiKeyValid() {

        return apiKey != null && !apiKey.trim().isEmpty();
    }

    public String sendRequest(String prompt) throws IOException {
        if (!isApiKeyValid()) {
            throw new IllegalStateException("API Key is not set or invalid.");
        }

        String jsonBody = JsonUtil.createJsonRequestBody(prompt);
        @SuppressWarnings("deprecation")
        URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/" + GEMINI_MODEL
                + ":generateContent?key=" + apiKey);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        connection.getOutputStream().write(jsonBody.getBytes(StandardCharsets.UTF_8));

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();
            return response;
        } else {
            throw new IOException("Falha na requisição com o código: " + responseCode);
        }
    }
}