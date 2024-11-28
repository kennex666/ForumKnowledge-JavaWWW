package com.fit.iuh.utilities;

import com.google.gson.Gson;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.*;
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

    public GeminiResponse generateContent() {

//        String apiPath =   System.getenv("GEMINI_API");
        String apiKey = System.getenv("GEMINI_API");
        String prompt = getPrompt();

        if (apiKey == null || prompt == null) {
            return null;
        }

        String responseContent;
        try {
            String requestBody = getRequestBody(prompt);
            responseContent = getHttpResponseBody(requestBody, apiKey);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(System.err);
            return null;
        }

        try {
            return parseResponse(responseContent);
        } catch (JAXBException e) {
            e.printStackTrace(System.err);
            return null;
        }

    }

    private String getAPIKey(String apiPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(apiPath))) {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    private String getPrompt() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("prompt/gemini-content-generator.xml")) {
            if (inputStream == null) {
                return null;
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    private String getRequestBody(String prompt) {
        Gson gson = new Gson();
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))));
        return gson.toJson(requestBody);
    }

    private String getHttpResponseBody(String requestBody, String apiKey) throws IOException, InterruptedException {
        String apiEndpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiEndpoint)).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

        Gson gson = new Gson();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        GeminiGenerationResponse geminiGenerationResponse = gson.fromJson(responseBody, GeminiGenerationResponse.class);
        return geminiGenerationResponse.getCandidates().get(0).getContent().getParts().get(0).getText();
    }

    private GeminiResponse parseResponse(String responseContent) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(GeminiResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        String xmlContent = responseContent.replaceAll("```xml|```", "").trim();
        return (GeminiResponse) unmarshaller.unmarshal(new StringReader(xmlContent));
    }

    public static void main(String[] args) throws JAXBException {
        GeminiContentGenerator generator = new GeminiContentGenerator();
        GeminiResponse response = generator.generateContent();

        System.out.println("Title: " + response.getTitle());
        System.out.println("Description: " + response.getDescription());
        System.out.println("Content: " + response.getContent());
    }

}
