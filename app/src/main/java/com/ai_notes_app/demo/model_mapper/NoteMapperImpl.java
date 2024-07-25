package com.ai_notes_app.demo.model_mapper;

import org.springframework.stereotype.Service;

import com.ai_notes_app.demo.controller.dto.CreateNoteRequest;
import com.ai_notes_app.demo.controller.dto.NoteDTO;
import com.ai_notes_app.demo.controller.dto.UpdateNoteRequest;
import com.ai_notes_app.demo.model.Note;
import com.ai_notes_app.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoteMapperImpl implements NoteMapper {

    private final UserService userService;

    @Override
    public Note toNote(CreateNoteRequest createNoteRequest,
                        String username) {
        if (createNoteRequest == null) {
            return null;
        }
        return new Note(createNoteRequest.getTitle(), 
                        createNoteRequest.getContent(),
                        userService.getUserIdByUsername(
                            username)
                        );
    }

    @Override
    public Note toNote(UpdateNoteRequest updateNoteRequest,
                        String username) {
        if (updateNoteRequest == null) {
            return null;
        }
        return new Note(updateNoteRequest.getTitle(), 
                        updateNoteRequest.getContent(),
                        userService.getUserIdByUsername(
                            username)
                        );
    }    

    @Override
    public NoteDTO toNoteDTO(Note note) {
        if (note == null) {
            return null;
        }
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent());
    }
}