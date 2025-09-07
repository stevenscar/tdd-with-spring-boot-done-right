package de.rieckpil.post;

import java.util.List;

public record Post(
    Long id, String title, String body, Long userId, List<String> tags, Reactions reactions) {}
