package com.ai_notes_app.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ai_notes_app.demo.config.SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME;
import com.ai_notes_app.demo.controller.dto.CreateNoteRequest;
import com.ai_notes_app.demo.controller.dto.NoteDTO;
import com.ai_notes_app.demo.controller.dto.UpdateNoteRequest;
import com.ai_notes_app.demo.model.Note;
import com.ai_notes_app.demo.model_mapper.NoteMapper;
import com.ai_notes_app.demo.service.NoteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteMapper noteMapper;

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public NoteDTO createNote(@Valid @RequestBody CreateNoteRequest createNoteRequest) {
        String username = getAuthenticatedUsername();

        Note note = noteMapper.toNote(createNoteRequest, username);
        return noteMapper.toNoteDTO(noteService.createNote(note));
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @PutMapping("/update")
    public NoteDTO update(@RequestBody UpdateNoteRequest updateNoteRequest) {
        String username = getAuthenticatedUsername();

        Note note = noteMapper.toNote(updateNoteRequest, username);
        return noteMapper.toNoteDTO(noteService.updateNote(note));
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @DeleteMapping("/delete/{noteId}")
    public void delete(@PathVariable Long noteId) {
        String username = getAuthenticatedUsername();

        noteService.deleteNote(noteId, username);
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @GetMapping("/get-notes")
    public List<NoteDTO> getNotesByUsername() {
        String username = getAuthenticatedUsername();
        List<Note> notes = noteService.getNotesByUsername(username);

        return notes.stream()
                .map(noteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }

    @Operation(security = {@SecurityRequirement(name = BASIC_AUTH_SECURITY_SCHEME)})
    @GetMapping("/get-all-notes")
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteService.getNotes();
        return notes.stream()
                .map(noteMapper::toNoteDTO)
                .collect(Collectors.toList());
    }
}