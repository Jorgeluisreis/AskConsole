package com.jorgeluisreis.askconsole.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import net.jorgedev.ConsoleClear;

public class ApiKeyManager {
    private static final String API_KEY_FILE = "api_key.ini";
    private String apiKey;

    public ApiKeyManager() {
        loadApiKey();
    }

    public boolean isApiKeyPresent() {
        return apiKey != null && !apiKey.isEmpty();
    }

    public String getApiKey() {
        return apiKey;
    }

    public void importApiKey() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo .ini contendo a chave API");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos .ini", "ini"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        if (line.startsWith("API=")) {
                            apiKey = line.substring(line.indexOf('=') + 1).trim();
                            ConsoleClear.run();
                            System.out.println("               _     _____                      _      ");
                            System.out.println("     /\\       | |   / ____|                    | |     ");
                            System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
                            System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
                            System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
                            System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
                            System.out.println("                                                        ");
                            System.out.println("                                                        ");
                            System.out.println("Consultando chave API...");
                            if (testApiKey(apiKey)) {
                                saveApiKey();
                                ConsoleClear.run();
                                System.out.println("Chave API importada com sucesso!");
                            } else {
                                apiKey = null;
                                System.out.println("Chave API inválida.");
                            }
                            return;
                        }
                    }
                    System.out.println("Chave API não encontrada no arquivo.");
                } catch (IOException e) {
                    ConsoleClear.run();
                    System.out.println("Erro ao ler o arquivo: " + e.getMessage());
                }
            } else {
                System.out.println("Arquivo não encontrado.");
            }
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    private boolean testApiKey(String apiKey) {
        try {
            GeminiApiClient client = new GeminiApiClient(apiKey);
            String response = client.sendRequest("teste");
            return response != null && !response.trim().isEmpty();
        } catch (IOException e) {
            ConsoleClear.run();
            System.out.println("               _     _____                      _      ");
            System.out.println("     /\\       | |   / ____|                    | |     ");
            System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
            System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
            System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
            System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
            System.out.println("                                                        ");
            System.out.println("                                                        ");
            System.out.println("Erro ao testar a chave API: " + e.getMessage());
            return false;
        }
    }

    public void loadApiKey() {
        File file = new File("api_key.ini");
        ConsoleClear.run();
        System.out.println("               _     _____                      _      ");
        System.out.println("     /\\       | |   / ____|                    | |     ");
        System.out.println("    /  \\   ___| | _| |     ___  _ __  ___  ___ | | ___ ");
        System.out.println("   / /\\ \\ / __| |/ / |    / _ \\| '_ \\/ __|/ _ \\| |/ _ \\");
        System.out.println("  / ____ \\ __ \\   <| |___| (_) | | | \\__ \\ (_) | |  __/");
        System.out.println(" /_/    \\_\\___/_|\\_\\\\_____\\___/|_| |_|___/\\___/|_|\\___|");
        System.out.println("                                                        ");
        System.out.println("                                                        ");
        if (file.exists()) {
            if (file.length() == 0) {
                System.out.println("Chave API inválida. Aguarde 3 segundos para ser redirecionado...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            } else {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        if (line.startsWith("API=")) {
                            String apiKeyFromFile = line.substring(line.indexOf('=') + 1).trim();
                            if (!apiKeyFromFile.isEmpty()) {
                                System.out.println("Consultando API...");
                                if (testApiKey(apiKeyFromFile)) {
                                    apiKey = apiKeyFromFile;
                                    System.out.println("Chave API válida.");
                                } else {
                                    System.out.println(
                                            "Chave API inválida. Aguarde 5 segundos para ser redirecionado...");
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            } else {
                                System.out.println("Chave API vazia. Aguarde 3 segundos para ser redirecionado...");
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                }
                            }

                            return;
                        }

                    }

                    System.out.println("Chave API não encontrada no arquivo.");
                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo de chave API: " + e.getMessage());
                }
            }
        } else {
        }
    }

    private void saveApiKey() {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new File(API_KEY_FILE))) {
            pw.println("API=" + apiKey);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a chave API: " + e.getMessage());
        }
    }
}