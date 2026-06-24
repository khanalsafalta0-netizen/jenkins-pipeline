package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://randomuser.me/api";
        Request request = new Request.Builder()
                    .url(url)
                    .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonResponse = response.body().string();
                System.out.println(jsonResponse);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(jsonResponse);
                JsonNode resultsNode = rootNode.get("results");
                JsonNode firstResultNode = resultsNode.get(0);
                JsonNode nameNode = firstResultNode.get("name");
                String name = nameNode.get("first").asText();
                System.out.println(name);
            }
        }
    }
}