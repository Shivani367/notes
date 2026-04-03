package com.example.notes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteRequest {

    private String title;
    private String content;
}