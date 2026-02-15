package com.sneha.realtimepoll.dto;

public class OptionResponse {

    private Long id;
    private String text;
    private int voteCount;

    public OptionResponse(Long id, String text, int voteCount) {
        this.id = id;
        this.text = text;
        this.voteCount = voteCount;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public int getVoteCount() { return voteCount; }
}
