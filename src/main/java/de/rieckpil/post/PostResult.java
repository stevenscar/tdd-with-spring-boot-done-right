package de.rieckpil.post;

import java.util.List;

public record PostResult(
  List<Post> posts,
  Long total,
  Long skip,
  Long limit) {
}
