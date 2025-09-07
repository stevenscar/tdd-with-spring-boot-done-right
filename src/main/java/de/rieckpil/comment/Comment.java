package de.rieckpil.comment;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record Comment(UUID id, String authorId, @NotBlank String content, LocalDate creationDate) {}
