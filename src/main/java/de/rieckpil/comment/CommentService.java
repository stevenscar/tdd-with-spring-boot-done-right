package de.rieckpil.comment;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class CommentService {

  public List<Comment> findAll() {
    // TODO: implement
    return List.of();
  }

  public UUID createComment(Comment comment, String authorName) {
    // TODO: implement
    return UUID.randomUUID();
  }
}
