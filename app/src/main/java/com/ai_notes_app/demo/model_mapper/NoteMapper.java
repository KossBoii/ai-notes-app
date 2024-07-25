package com.ai_notes_app.demo.model_mapper;

import com.ai_notes_app.demo.controller.dto.CreateNoteRequest;
import com.ai_notes_app.demo.controller.dto.NoteDTO;
import com.ai_notes_app.demo.controller.dto.UpdateNoteRequest;
import com.ai_notes_app.demo.model.Note;


public interface NoteMapper {

    Note toNote(CreateNoteRequest createNoteRequest, String username);

    Note toNote(UpdateNoteRequest updateNoteRequest, String username);

    NoteDTO toNoteDTO(Note note);
}