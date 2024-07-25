package com.ai_notes_app.demo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

    @Schema(example = "username")
    @NotBlank
    private String username;

    @Schema(example = "password")
    @NotBlank
    private String password;

    @Schema(example = "name")
    @NotBlank
    private String name;

    @Schema(example = "username@gmail.com")
    @Email
    private String email;
}