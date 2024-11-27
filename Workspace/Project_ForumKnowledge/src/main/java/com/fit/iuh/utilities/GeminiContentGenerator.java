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
            GeminiGenerationResponse geminiGenerationResponse = gsonConverter.fromJson(responseBody, GeminiGenerationResponse.class);

            String textResult = geminiGenerationResponse.getCandidates().get(0).getContent().getParts().get(0).getText();

            return textResult;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public static void main(String[] args) throws JAXBException {
        GeminiContentGenerator generator = new GeminiContentGenerator();
        String content = generator.generateContent();
        System.out.println(content);

        String xmlContent = content.replaceAll("```xml|```", "").trim();

        JAXBContext context = JAXBContext.newInstance(GeminiResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        GeminiResponse response = (GeminiResponse) unmarshaller.unmarshal(new StringReader(xmlContent));

        System.out.println("Title: " + response.getTitle());
        System.out.println("Description: " + response.getDescription());
        System.out.println("Content: " + response.getContent());
    }

}
