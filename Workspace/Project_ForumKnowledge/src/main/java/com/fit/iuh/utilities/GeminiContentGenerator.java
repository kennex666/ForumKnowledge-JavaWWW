package com.fit.iuh.utilities;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiContentGenerator {

    public String generateContent() {

        String apiPath = "D:\\09_ETC\\API_KEY\\gemini_api_key.txt";

        String apiKey;
        try (BufferedReader reader = new BufferedReader(new FileReader(apiPath))) {
            apiKey = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }

        String prompt;
        // Get the file content in project-root/resources/prompt/gemini-content-generator.xml
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("prompt/gemini-content-generator.xml");) {
            if (inputStream == null) {
                return null;
            }

            prompt = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", prompt)
                ))
        ));

        Gson gson = new Gson();
        String json = gson.toJson(requestBody);

        String apiEndpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            Gson gsonConverter = new Gson();
            GeminiResponse geminiResponse = gsonConverter.fromJson(responseBody, GeminiResponse.class);

            String textResult = geminiResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

            return textResult;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void main(String[] args) {
        GeminiContentGenerator generator = new GeminiContentGenerator();
        System.out.println(generator.generateContent());
    }

}
