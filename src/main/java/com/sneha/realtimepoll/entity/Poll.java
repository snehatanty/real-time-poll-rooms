package com.sneha.realtimepoll.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String question;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
