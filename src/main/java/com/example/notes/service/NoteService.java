package com.example.notes.service;

import com.example.notes.entity.Note;
import com.example.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> listNotes(String userId) {
        return noteRepository.findByUserId(userId);
    }

    public Note getNote(String id, String userId) {
    return noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note not found"));
}

    public Note createNote(String title, String content, String userId) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);
        note.setCreatedAt(Instant.now());
        note.setUpdatedAt(Instant.now());

        return noteRepository.save(note);
    }

    public Note updateNote(String id, String title, String content, String userId) {
        Note note = getNote(id, userId);
        note.setTitle(title);
        note.setContent(content);
        note.setUpdatedAt(Instant.now());

        return noteRepository.save(note);
    }

    public void deleteNote(String id, String userId) {
        Note note = getNote(id, userId);
        noteRepository.delete(note);
    }
}