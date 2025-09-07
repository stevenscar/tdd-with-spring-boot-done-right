package de.rieckpil.comment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import de.rieckpil.configuration.WebSecurityConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Import(WebSecurityConfiguration.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private CommentService commentService;

  @Test
  @DisplayName("Should allow anonymous users to get all comments")
  void shouldAllowAnonymousUsersToGetAllComments() throws Exception {

    when(commentService.findAll())
        .thenReturn(
            List.of(
                new Comment(UUID.randomUUID(), "1", "Hello World A", LocalDate.now()),
                new Comment(UUID.randomUUID(), "2", "Hello World B", LocalDate.now().minusDays(1)),
                new Comment(
                    UUID.randomUUID(), "2", "Hello World C", LocalDate.now().minusDays(2))));

    mockMvc
        .perform(get("/api/comments").header(ACCEPT, APPLICATION_JSON_VALUE))
        .andExpect(status().is(200))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.length()").value(3))
        .andExpect(jsonPath("$[0].content").exists())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].creationDate").exists())
        .andExpect(jsonPath("$[0].authorId").exists())
        .andExpect(jsonPath("$[1].content").exists())
        .andExpect(jsonPath("$[1].id").exists())
        .andExpect(jsonPath("$[1].creationDate").exists())
        .andExpect(jsonPath("$[1].authorId").exists())
        .andExpect(jsonPath("$[2].content").exists())
        .andExpect(jsonPath("$[2].id").exists())
        .andExpect(jsonPath("$[2].creationDate").exists())
        .andExpect(jsonPath("$[2].authorId").exists())
        .andDo(print())
        .andReturn();
  }

  @Test
  @DisplayName("Should reject anonymous users when creating comments")
  void shouldRejectAnonymousUsersWhenCreatingComments() throws Exception {

    mockMvc
        .perform(
            post("/api/comments")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON)
                .content(
                    """
            {
              "id": "4e1c5f6a-3b7a-4b8e-8c5a-5f6a3b7a4b8e",
              "authorId": "1",
              "content": "Hello World X",
              "creationDate": "2023-01-01"
            }
          """))
        .andExpect(status().isUnauthorized())
        .andDo(print())
        .andReturn();
  }

  @Test
  @WithMockUser(
      username = "duke",
      roles = {"VISITOR"})
  @DisplayName("Should reject authenticated user without ADMIN role when creating comments")
  void shouldRejectAuthenticatedUserWithoutAdminRoleWhenCreatingComments() throws Exception {

    mockMvc
        .perform(
            post("/api/comments")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON)
                // .with(user("duke").roles("VISITOR"))
                .content(
                    """
            {
              "id": "4e1c5f6a-3b7a-4b8e-8c5a-5f6a3b7a4b8e",
              "authorId": "1",
              "content": "Hello World X",
              "creationDate": "2023-01-01"
            }
          """))
        .andExpect(status().isForbidden())
        .andDo(print())
        .andReturn();
  }

  @Test
  @WithMockUser(
      username = "duke",
      roles = {"VISITOR", "ADMIN"})
  @DisplayName("Should fail when comment data is invalid")
  void shouldFailOnInvalidCommentData() throws Exception {
    mockMvc
        .perform(
            post("/api/comments")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON)
                .content(
                    """
            {
              "id": "4e1c5f6a-3b7a-4b8e-8c5a-5f6a3b7a4b8e",
              "authorId": "1",
              "content": "",
              "creationDate": "2023-01-01"
            }
          """))
        .andExpect(status().isBadRequest())
        .andDo(print())
        .andReturn();
  }

  @Test
  @WithMockUser(
      username = "duke",
      roles = {"VISITOR", "ADMIN"})
  @DisplayName("Should create comment when user is authenticated and has ADMIN role")
  void shouldCreateCommentWhenUserIsAuthenticatedAndAdmin() throws Exception {

    UUID uuid = UUID.randomUUID();

    when(commentService.createComment(any(), anyString())).thenReturn(uuid);

    mockMvc
        .perform(
            post("/api/comments")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .contentType(APPLICATION_JSON)
                .content(
                    """
            {
              "id": "%s",
              "authorId": "1",
              "content": "Hello World X",
              "creationDate": "2023-01-01"
            }
          """
                        .formatted(uuid)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(
            header()
                .string("Location", Matchers.containsString("/api/comments/%s".formatted(uuid))))
        .andDo(print())
        .andReturn();
  }
}
