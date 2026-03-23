package com.example.notes.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateNoteRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public String getTitle() { return title; }
    public String getContent() { return content; }

    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
}