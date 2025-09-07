package de.rieckpil.comment;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.UUID;

/**
 * Develop a REST API to retrieve and create comments. Everybody should be able to retrieve comments
 * but only logged-in users with the role ADMIN can create a comment.
 */
@RestController
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/api/comments")
  public List<Comment> getAllComments() {
    return commentService.findAll();
  }

  @PostMapping("/api/comments")
  public ResponseEntity<Void> createComment(
    @Valid @RequestBody Comment comment,
    Authentication authentication,
    UriComponentsBuilder uriComponentsBuilder) {

    UUID newCommentId = commentService.createComment(comment, authentication.getName());

    UriComponents uriComponents = uriComponentsBuilder
      .path("/api/comments/{id}")
      .buildAndExpand(newCommentId);

    return ResponseEntity.created(uriComponents.toUri()).build();
  }

}
