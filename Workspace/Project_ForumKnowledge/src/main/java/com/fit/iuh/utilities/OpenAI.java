package com.fit.iuh.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class OpenAI {


    public static List<Map<String, String>> getPromptReviewPost() {
        return new ArrayList<>(Arrays.asList(
                Map.of("role", "system", "content", "Bạn là một người kiểm duyệt viên của trang diễn đàn. Bạn sẽ có nhiệm vụ kiểm soát người dùng có đang spam, đăng nội dung lăng mạ, nhạy cảm hay không." +
                        "\n Bạn sẽ trả về chuỗi JSON và không giải thích gì thêm.\n" +
                        "Chuỗi JSON, mẫu {\"status\": 200, \"message\": \"Bài đăng hợp lệ\"}" +
                        "\n Status: 200 là duyệt bài, 400 là bài đăng bị từ chối. - message là lí do bị từ chối."),
                Map.of("role", "user", "content", "âbsjahshaks"),
                Map.of("role", "assistant", "content", "{\"status\": 400, \"message\": \"Spam\"}"),
                Map.of("role", "user", "content", "Tìm người yêu Sài Gòn"),
                Map.of("role", "assistant", "content", "{\"status\": 400, \"message\": \"Spam\"}"),
                Map.of("role", "user", "content", "JavaScript là một ngôn ngữ lập trình phổ biến và mạnh mẽ, chủ yếu được sử dụng để phát triển các trang web động. Với khả năng tương tác trực tiếp với HTML và CSS, JavaScript giúp tạo ra các hiệu ứng, hoạt ảnh, và xử lý sự kiện người dùng trên trình duyệt. Bên cạnh việc sử dụng trong phát triển web, JavaScript còn được áp dụng trong các ứng dụng di động, server-side (với Node.js), và thậm chí trong Internet of Things (IoT). Với sự hỗ trợ rộng rãi từ các thư viện và framework như React, Angular, và Vue, JavaScript là lựa chọn hàng đầu cho các nhà phát triển web hiện đại."),
                Map.of("role", "assistant", "content", "{\"status\": 200, \"message\": \"Bài đã được duyệt\"}")
        ));
    }


    public static String callGPT(String content, List chatCompletion) throws Exception {
        String apiEndpoint = "https://api.openai.com/v1/chat/completions";

        // Tạo client và yêu cầu POST
        HttpClient client = HttpClient.newHttpClient();

        // Dữ liệu yêu cầu (body)
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("model", "gpt-4o-mini");
        requestData.put("max_tokens", 1000);
        requestData.put("messages", chatCompletion);

        System.out.println("API_KEY" + System.getenv("OPEN_AI_KEY"));
        // Chuyển đổi dữ liệu yêu cầu thành JSON
        String jsonRequest = new Gson().toJson(requestData);

        // Tạo yêu cầu HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + (System.getenv("OPEN_AI_KEY") == null ? "" : System.getenv("OPEN_AI_KEY")))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        // Gửi yêu cầu và nhận phản hồi
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Kiểm tra mã trạng thái và trả về phản hồi
        if (response.statusCode() == 200) {
            // parse json

            String json = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);

            String jsonData = rootNode.path("choices").path(0).path("message").path("content").asText();

            return jsonData;  // Trả về nội dung phản hồi
        } else {

            return "Error: " + response.statusCode();  // Trả về lỗi nếu có
        }
    }

    public static String generateContent(String query){
        try {
            List chatCompletion = new ArrayList<>();
            chatCompletion.add(
                    Map.of("role", "system", "content", "Bạn là một nhà văn, bạn có thể viết hướng dẫn về mọi lĩnh vực. Bạn luôn sử dụng Markdown cho mọi bài viết. Người dùng sẽ hỏi bạn, và nhiệm vụ của bạn là sử dụng ý tưởng đó để viết thành bài viết."));
            chatCompletion.add(
                    Map.of("role", "user", "content", query)
            );
            return callGPT(query, chatCompletion);
//            return "**Hello**\n# Hello";
        } catch (Exception e) {
            e.printStackTrace();
            return new Gson().toJson(Map.of("status", 100, "message", "Unexpected GPT error!"));
        }
    }

    public static String autoReview(String content, String type) throws Exception {
        try {
            switch (type) {
                case "post":
                    List chatCompletion = getPromptReviewPost();
                    chatCompletion.add(
                            Map.of("role", "user", "content", content)
                    );
                    return callGPT(content, chatCompletion);
                default:
                    return new Gson().toJson(Map.of("status", 100, "message", "Unexpected GPT error!"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Gson().toJson(Map.of("status", 100, "message", "Unexpected GPT error!"));
        }
    }

    public static Map parseJson(String json){
        return new Gson().fromJson(json, Map.class);
    }

    public static void main(String[] args) {
        try {
            System.out.println(autoReview("", "post"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
