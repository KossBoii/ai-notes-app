package com.ai_notes_app.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import com.ai_notes_app.demo.model.Note;
import com.ai_notes_app.demo.repository.NoteRepository;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    
    private final NoteRepository noteRepository;

    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<Note> getNotesByUserId(Long userId) {
        return noteRepository.findByUserId(userId);
    }
}