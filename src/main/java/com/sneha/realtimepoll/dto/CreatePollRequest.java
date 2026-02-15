package com.sneha.realtimepoll.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CreatePollRequest {

    private String question;
    private List<String> options;
    private LocalDateTime expiresAt;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
