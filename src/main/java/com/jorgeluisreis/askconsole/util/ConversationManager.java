package com.jorgeluisreis.askconsole.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConversationManager {

    private static final String CONVERSATION_FOLDER = "conversations/";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ConversationManager() {
        File folder = new File(CONVERSATION_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String generateHash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void saveMessage(String hash, String userMessage, String botMessage, LocalDateTime userTime,
            LocalDateTime botTime) throws IOException {
        String filePath = CONVERSATION_FOLDER + hash + ".json";
        Path path = Paths.get(filePath);

        JSONObject conversationJson;
        if (Files.exists(path)) {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            conversationJson = new JSONObject(content);
        } else {
            conversationJson = new JSONObject();
            conversationJson.put("hash", hash);
            conversationJson.put("title", "Tema da conversa");
            conversationJson.put("messages", new JSONArray());
        }

        JSONArray messagesArray = conversationJson.getJSONArray("messages");

        JSONObject userMessageJson = new JSONObject();
        userMessageJson.put("id", messagesArray.length() + 1);
        userMessageJson.put("sender", "Eu");
        userMessageJson.put("content", userMessage);
        userMessageJson.put("time", userTime.format(formatter));
        messagesArray.put(userMessageJson);

        JSONObject botMessageJson = new JSONObject();
        botMessageJson.put("id", messagesArray.length() + 1);
        botMessageJson.put("sender", "IA");
        botMessageJson.put("content", botMessage);
        botMessageJson.put("time", botTime.format(formatter));
        messagesArray.put(botMessageJson);

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(conversationJson.toString(4));
        }
    }

    public List<String> listConversations() {
        File folder = new File(CONVERSATION_FOLDER);
        List<String> conversations = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                conversations.add(file.getName());
            }
        }
        return conversations;
    }

    public String loadConversation(String hash) throws IOException {
        String filePath = CONVERSATION_FOLDER + hash + ".json";
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Arquivo de conversa não encontrado.");
        }

        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IOException("Erro ao carregar conversa: " + e.getMessage());
        }
    }
}