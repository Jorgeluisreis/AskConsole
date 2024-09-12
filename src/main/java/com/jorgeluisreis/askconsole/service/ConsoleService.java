package com.jorgeluisreis.askconsole.service;

import static org.fusesource.jansi.Ansi.ansi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jorgeluisreis.askconsole.client.GeminiApiClient;
import com.jorgeluisreis.askconsole.util.ConversationManager;
import com.jorgeluisreis.askconsole.util.JsonUtil;
import com.jorgeluisreis.askconsole.util.TextFormatter;

public class ConsoleService {

    private final GeminiApiClient geminiApiClient;
    private final ConversationManager conversationManager;
    private final String hash;
    private String conversationHistory = "";

    public ConsoleService(GeminiApiClient geminiApiClient, ConversationManager conversationManager, String hash) {
        this.geminiApiClient = geminiApiClient;
        this.conversationManager = conversationManager;
        this.hash = hash;
    }

    public String chat(String userMessage) {
        try {
            String fullPrompt = conversationHistory + "\nEu: " + userMessage;

            String response = geminiApiClient.sendRequest(fullPrompt);

            String botMessage = JsonUtil.parseGeminiResponse(response);

            if (botMessage == null || botMessage.isEmpty()) {
                botMessage = "Erro: Resposta da IA sem conteúdo.";
            }

            conversationHistory += "\nEu: " + userMessage + "\nIA: " + botMessage;

            conversationManager.saveMessage(hash, userMessage, botMessage, LocalDateTime.now(), LocalDateTime.now());

            return botMessage;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return "Erro ao processar a solicitação ou a resposta da IA";
        }
    }

    public String getConversationHistory(String hash) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        File file = new File("conversations/" + hash + ".json");
        System.out.println(ansi().fgBrightGreen().a("Hash da Conversa: " + ansi().fgDefault().a(hash)));

        if (file.exists() && file.isFile()) {
            try {
                String jsonContent = new String(Files.readAllBytes(file.toPath()));

                jsonContent = jsonContent.trim();

                if (jsonContent.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(jsonContent);
                    JSONArray messages = jsonObject.getJSONArray("messages");

                    if (messages.length() > 0) {
                        System.out.println(ansi().fgYellow().a("===== Histórico de Conversa ====="));

                        StringBuilder formattedHistory = new StringBuilder();
                        for (int i = 0; i < messages.length(); i++) {
                            JSONObject message = messages.getJSONObject(i);
                            String time = message.getString("time");
                            String sender = message.getString("sender");
                            String content = message.getString("content");

                            LocalDateTime messageTime = LocalDateTime.parse(time,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                            String formattedTime = messageTime.format(formatter);

                            if (sender.equals("Eu")) {
                                formattedHistory
                                        .append(ansi().fgGreen().a(formattedTime + " - " + sender + ": ").reset()
                                                .toString()
                                                + TextFormatter.formatText(content) + "\n");
                            } else if (sender.equals("IA")) {
                                formattedHistory
                                        .append(ansi().fgCyan().a(formattedTime + " - " + sender + ": ").reset()
                                                .toString()
                                                + TextFormatter.formatText(content) + "\n");
                            } else {
                                formattedHistory.append(
                                        ansi().fgDefault().a(formattedTime + " - " + sender + ": ").reset().toString()
                                                + TextFormatter.formatText(content) + "\n");
                            }
                            formattedHistory.append(
                                    ansi().fgDefault().a("------------------------------------").reset().toString()
                                            + "\n");
                        }
                        return formattedHistory.toString();
                    } else {
                        return "Nenhuma conversa anterior encontrada. Iniciando um novo chat...";
                    }
                } else {
                    System.out.println(ansi().fgRed()
                            .a("Erro ao carregar o histórico da conversa: O arquivo não está no formato JSON correto.")
                            .reset());
                    return "";
                }
            } catch (IOException | JSONException e) {
                System.out.println(
                        ansi().fgRed().a("Erro ao carregar o histórico da conversa: " + e.getMessage()).reset());
                return "";
            }
        }
        return "Iniciando um novo chat...";
    }

    public void loadConversationHistory(String conversation) {
        try {
            JSONObject conversationJson = new JSONObject(conversation);
            JSONArray messagesArray = conversationJson.getJSONArray("messages");

            StringBuilder conversationHistoryBuilder = new StringBuilder();
            for (int i = 0; i < messagesArray.length(); i++) {
                JSONObject messageJson = messagesArray.getJSONObject(i);
                conversationHistoryBuilder.append(messageJson.getString("sender")).append(": ")
                        .append(messageJson.getString("content")).append("\n");
            }

            this.conversationHistory = conversationHistoryBuilder.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void clearHistory() {
        conversationHistory = "";
    }
}