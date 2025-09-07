package de.rieckpil.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Develop an HTTP client that fetch all posts from the post-service and return them as a list. The
 * post-service returns the all posts with pagination. The client should fetch all pages and return
 * the result as a list.
 */
@Component
public class PostClient {

  private final RestTemplate restTemplate;

  public PostClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<Post> fetchAllPost() {

    List<Post> allPosts = new ArrayList<>();

    long skip = 0;
    long limit = 30;
    long total;

    do {
      PostResult postResult =
          restTemplate.getForObject(
              "/posts?limit={limit}&skip={skip}", PostResult.class, limit, skip);
      if (postResult == null || postResult.posts() == null) {
        break;
      }
      allPosts.addAll(postResult.posts());
      total = postResult.total();
      skip += limit;
    } while (skip < total);

    return allPosts;
  }
}
