package com.fit.iuh.utilities;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringToUrl {
    public static String convertToUrlFormat(String input) {
        // Normalize the string (remove accents)
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);

        // Remove non-ASCII characters (diacritics)
        normalized = normalized.replaceAll("[^\\p{ASCII}]", "");

        // Convert to lowercase
        normalized = normalized.toLowerCase();

        // Replace spaces with hyphens
        normalized = normalized.replaceAll("\\s+", "-");

        // Remove any remaining unwanted characters
        normalized = normalized.replaceAll("[^a-z0-9\\-]", "");

        return normalized;
    }

    public static void main(String[] args) {
        String input = "Xin chào tôi là Bảo";
        String urlFormatted = convertToUrlFormat(input);
        System.out.println(urlFormatted);  // Output: xin-chao-toi-la-bao
    }
}
