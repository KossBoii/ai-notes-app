package com.ai_notes_app.demo.service;

import java.util.List;

import com.ai_notes_app.demo.model.Note;

public interface NoteService {
    Note createNote(Note note);

    Note updateNote(Note note);

    void deleteNote(Long id, String username);

    List<Note> getNotesByUsername(String username);

    List<Note> getNotes();
}
