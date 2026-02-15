package com.sneha.realtimepoll.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PollResponse {

    private String id;
    private String question;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private List<OptionResponse> options;

    public PollResponse(String id, String question,
                        LocalDateTime createdAt,
                        LocalDateTime expiresAt,
                        List<OptionResponse> options) {
        this.id = id;
        this.question = question;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.options = options;
    }

    public String getId() { return id; }
    public String getQuestion() { return question; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public List<OptionResponse> getOptions() { return options; }
}
