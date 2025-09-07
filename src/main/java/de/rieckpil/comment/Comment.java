package de.rieckpil.comment;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public record Comment(
  UUID id,
  String authorId,
  @NotBlank String content,
  LocalDate creationDate) {
}
