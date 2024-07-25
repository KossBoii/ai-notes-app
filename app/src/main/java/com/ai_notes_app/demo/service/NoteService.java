package com.ai_notes_app.demo.service;

import com.ai_notes_app.demo.model.Note;

import java.util.List;

public interface NoteService {
    Note createNote(Note note);

    Note updateNote(Note note);

    void deleteNote(Long id);

    List<Note> getNotesByUserId(Long userId);
}
