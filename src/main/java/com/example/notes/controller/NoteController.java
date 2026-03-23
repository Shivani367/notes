package com.example.notes.controller;

import com.example.notes.dto.CreateNoteRequest;
import com.example.notes.dto.UpdateNoteRequest;
import com.example.notes.entity.Note;
import com.example.notes.service.NoteService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<Note>> listNotes() {
        return ResponseEntity.ok(noteService.listNotes(getUser()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id) {
        return ResponseEntity.ok(noteService.getNote(id, getUser()));
    }

  @PostMapping
public ResponseEntity<Note> createNote(@Valid @RequestBody CreateNoteRequest request) {

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

    private String getUser() {
        return "test-user"; // will replace with JWT later
    }
}