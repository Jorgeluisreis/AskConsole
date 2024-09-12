package com.jorgeluisreis.askconsole;

import java.io.IOException;
import com.jorgeluisreis.askconsole.client.ApiKeyManager;
import com.jorgeluisreis.askconsole.client.GeminiApiClient;
import com.jorgeluisreis.askconsole.service.ConsoleService;

public class Main {

    public static void main(String[] args) throws IOException {

        ApiKeyManager apiKeyManager = new ApiKeyManager();
        GeminiApiClient geminiApiClient = null;
        ConsoleService consoleService = null;

        if (apiKeyManager.isApiKeyPresent()) {
            geminiApiClient = new GeminiApiClient(apiKeyManager.getApiKey());
            consoleService = new ConsoleService(geminiApiClient, null, null);
        } else {
            consoleService = new ConsoleService(null, null, null);
        }

        MenuManager menuManager = new MenuManager(consoleService, apiKeyManager);

        menuManager.displayMenu();
    }
}