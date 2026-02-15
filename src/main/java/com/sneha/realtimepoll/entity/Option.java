package com.sneha.realtimepoll.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "poll_option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private int voteCount = 0;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
