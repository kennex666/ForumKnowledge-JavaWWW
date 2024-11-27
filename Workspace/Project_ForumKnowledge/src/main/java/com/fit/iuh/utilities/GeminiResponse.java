package com.fit.iuh.utilities;

import java.util.List;

public class GeminiResponse {
    private List<Candidate> candidates;

    // Getter và Setter
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public static class Candidate {
        private Content content;

        // Getter và Setter
        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    public static class Content {
        private List<Part> parts;

        // Getter và Setter
        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }
    }

    public static class Part {
        private String text;

        // Getter và Setter
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
