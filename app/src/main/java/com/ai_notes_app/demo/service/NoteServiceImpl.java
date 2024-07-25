package com.ai_notes_app.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ai_notes_app.demo.exception.notes.DuplicateNoteTitleException;
import com.ai_notes_app.demo.exception.notes.NoteNotFoundException;
import com.ai_notes_app.demo.exception.users.UserNotFoundException;
import com.ai_notes_app.demo.model.Note;
import com.ai_notes_app.demo.model.User;
import com.ai_notes_app.demo.repository.NoteRepository;
import com.ai_notes_app.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public Note createNote(Note note) {
        Optional<Note> existingNote = noteRepository.findByTitleAndUserId(
            note.getTitle(), note.getUserId()
        );

        if (existingNote.isPresent()) {
            throw new DuplicateNoteTitleException(
                String.format("A note with the title '%s' already exists for user ID %d", 
                              note.getTitle(), note.getUserId()));
        }

        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Note note) {
        List<Note> notes = noteRepository.findByTitleExactOrContainingAndUserId(
            note.getTitle(), note.getUserId());

        if (notes.isEmpty()) {
            throw new NoteNotFoundException(
                String.format("Note with title '%s' for user ID %d not found", 
                              note.getTitle(), note.getUserId()));
        }

        // Assuming the first note in the list is the one to update
        Note existingNote = notes.get(0);

        // Update the fields of the existing note
        existingNote.setTitle(note.getTitle());
        existingNote.setContent(note.getContent());
        existingNote.setUserId(note.getUserId());

        // Save the updated note
        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(Long id, String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UserNotFoundException(
                                      String.format("User with username %s not found", username)));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            // ADMIN can delete any note
            Optional<Note> note = noteRepository.findById(id);
            if (note.isEmpty()) {
                throw new NoteNotFoundException(
                    String.format("Note with ID %d not found", id));
            }
            noteRepository.delete(note.get());   
        } else {
            // Regular user can only delete their own notes
            Optional<Note> note = noteRepository.findByIdAndUserId(id, user.getId());

            if (note.isEmpty()) {
                throw new NoteNotFoundException(
                    String.format("Note with ID %d for user %s not found", id, username));
            }
    
            noteRepository.delete(note.get());
        }
    }

    @Override
    public List<Note> getNotesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                    .orElseThrow(() -> new UserNotFoundException(
                                        String.format("User with username %s not found", username)));
        
        return noteRepository.findByUsername(username);
    }

    @Override
    public Note createGeneratedNote(String title, String content, Long userId) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUserId(userId);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }
}