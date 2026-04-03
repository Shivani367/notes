package com.example.notes.controller;

import com.example.notes.dto.CreateNoteRequest;
import com.example.notes.dto.UpdateNoteRequest;
import com.example.notes.entity.Note;
import com.example.notes.service.NoteService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

// 🔐 NEW IMPORTS (IMPORTANT)
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> listNotes() {
        return ResponseEntity.ok(noteService.listNotes(getUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id) {
        return ResponseEntity.ok(noteService.getNote(id, getUser()));
    }

    @PostMapping
    public ResponseEntity<Note> createNote(
            @Valid @RequestBody CreateNoteRequest request) {

        Note note = noteService.createNote(
                request.getTitle(),
                request.getContent(),
                getUser()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable String id,
            @RequestBody UpdateNoteRequest request) {

        return ResponseEntity.ok(
                noteService.updateNote(
                        id,
                        request.getTitle(),
                        request.getContent(),
                        getUser()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id, getUser());
        return ResponseEntity.noContent().build();
    }

    // 🔥 UPDATED METHOD (THIS IS THE MAIN CHANGE)
    private String getUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Object principal = auth.getPrincipal();

    if (principal instanceof OAuth2User user) {
        return user.getAttribute("email"); // better than "sub"
    }

    throw new RuntimeException("User not authenticated");
}
}