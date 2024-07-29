package com.edu.iuh.shop_managerment.services;

import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class GoogleUserInfoService {

    private static final String USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public String getUserInfoFromGoogle(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(USERINFO_URL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
    public String getUserEmail(String accessToken) throws IOException, InterruptedException {
        String userInfoJson = getUserInfoFromGoogle(accessToken);
        JSONObject jsonObject = new JSONObject(userInfoJson);
        return jsonObject.getString("email");
    }
}