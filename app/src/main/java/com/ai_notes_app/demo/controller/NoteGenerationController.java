package com.ai_notes_app.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai_notes_app.demo.model.Note;
import com.ai_notes_app.demo.service.ChatGPTNoteService;
import com.ai_notes_app.demo.service.NoteService;
import com.ai_notes_app.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notes")
public class NoteGenerationController {

    private final ChatGPTNoteService chatGPTNoteService;
    private final NoteService noteService;
    private final UserService userService;

    private String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping("/generate-notes")
    public ResponseEntity<String> generateNote(@RequestParam String title,
                                                @RequestParam String prompt,
                                                @RequestParam(defaultValue = "gpt-3.5-turbo") String model) {
        String username = getAuthenticatedUsername();
        Long userId = userService.getUserIdByUsername(username); // Implement this method in NoteService
                                                    
        try {
            // Generate content using ChatGPT
            String generatedContent = chatGPTNoteService.generateNoteContent(prompt, model);

            // Save the generated note
            Note note = noteService.createGeneratedNote(title, generatedContent, userId);

            return ResponseEntity.status(HttpStatus.CREATED).body("Note created successfully with ID: " + note.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate and save note");
        }
    }
}
