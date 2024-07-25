package com.ai_notes_app.demo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNoteRequest {
    @Schema(example = "example title")
    @NotBlank
    private String title;

    @Schema(example = "example content")
    @NotBlank
    private String content;
}
