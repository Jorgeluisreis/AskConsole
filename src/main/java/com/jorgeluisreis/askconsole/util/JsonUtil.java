package com.jorgeluisreis.askconsole.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static String createJsonRequestBody(String prompt) {
        JSONObject promptJson = new JSONObject();

        JSONArray contentsArray = new JSONArray();
        JSONObject contentsObject = new JSONObject();
        contentsObject.put("role", "user");

        JSONArray partsArray = new JSONArray();
        JSONObject partsObject = new JSONObject();
        partsObject.put("text", prompt);
        partsArray.put(partsObject);
        contentsObject.put("parts", partsArray);

        contentsArray.put(contentsObject);
        promptJson.put("contents", contentsArray);

        JSONArray safetySettingsArray = new JSONArray();
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_HATE_SPEECH", "BLOCK_ONLY_HIGH");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_ONLY_HIGH");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_ONLY_HIGH");
        addSafetySetting(safetySettingsArray, "HARM_CATEGORY_HARASSMENT", "BLOCK_ONLY_HIGH");

        promptJson.put("safetySettings", safetySettingsArray);

        JSONObject parametersJson = new JSONObject();
        parametersJson.put("temperature", 0.5);
        parametersJson.put("topP", 0.99);
        promptJson.put("generationConfig", parametersJson);

        return promptJson.toString();
    }

    public static String parseGeminiResponse(String jsonResponse) throws JSONException {
        if (jsonResponse == null || jsonResponse.isEmpty()) {
            throw new JSONException("Resposta vazia ou null.");
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (!jsonObject.has("candidates")) {
                throw new JSONException("Chave 'candidates' não encontrada na resposta.");
            }

            JSONArray candidatesArray = jsonObject.getJSONArray("candidates");
            if (candidatesArray.length() == 0) {
                throw new JSONException("Nenhum candidato encontrado.");
            }

            JSONObject candidateObject = candidatesArray.getJSONObject(0);

            if (!candidateObject.has("content")) {
                throw new JSONException("Chave 'content' não encontrada no candidato.");
            }

            JSONArray partsArray = candidateObject.getJSONObject("content").optJSONArray("parts");
            if (partsArray == null || partsArray.length() == 0) {
                throw new JSONException("Nenhuma parte encontrada no conteúdo.");
            }

            JSONObject partObject = partsArray.optJSONObject(0);
            if (partObject == null) {
                throw new JSONException("Nenhuma parte encontrada no conteúdo.");
            }
            return partObject.getString("text");
        } catch (JSONException e) {
            throw new JSONException("Erro ao parsear resposta: " + e.getMessage());
        }
    }

    private static void addSafetySetting(JSONArray array, String category, String threshold) {
        JSONObject setting = new JSONObject();
        setting.put("category", category);
        setting.put("threshold", threshold);
        array.put(setting);
    }
}