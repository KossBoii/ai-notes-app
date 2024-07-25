package com.ai_notes_app.demo.exception.notes;

public class DuplicateNoteTitleException extends RuntimeException {
    public DuplicateNoteTitleException(String message) {
        super(message);
    }
}