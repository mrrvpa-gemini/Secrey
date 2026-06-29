package com.darkuniverse.utils;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class TelegramBot {
    private static final String BOT_TOKEN = "8774991694:AAG7Yp8v8nnvQg2lLLB_sTuVF2_-CltPUNg";
    private static final String OWNER_ID = "8469659582";
    private static final String BASE_URL = "https://api.telegram.org/bot" + BOT_TOKEN + "/";
    private final OkHttpClient client = new OkHttpClient();

    public JSONObject createAccount(String username, String password, String expired, String role) {
        String text = "✅ *Account Created!*\n" +
                      "👤 Username: `" + username + "`\n" +
                      "🔑 Password: `" + password + "`\n" +
                      "📅 Expired: " + expired + "\n" +
                      "🎯 Role: " + role + "\n\n" +
                      "📱 *Dark Universe Panel*";
        return sendMessage(OWNER_ID, text);
    }

    public JSONObject sendMessage(String chatId, String text) {
        try {
            RequestBody body = new FormBody.Builder()
                .add("chat_id", chatId)
                .add("text", text)
                .add("parse_mode", "Markdown")
                .build();

            Request request = new Request.Builder()
                .url(BASE_URL + "sendMessage")
                .post(body)
                .build();

            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            response.close();
            return new JSONObject(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject().put("ok", false);
        }
    }
}
