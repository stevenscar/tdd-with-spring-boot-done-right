package de.rieckpil.post;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PostClientTest {

  @RegisterExtension
  static WireMockExtension mockServer =
    WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private PostClient cut = new PostClient(new RestTemplateBuilder().rootUri(mockServer.baseUrl()).build());

  @Test
  @DisplayName("Should return all posts when calling GET /posts")
  void shouldReturnAllPostsWhenCallingGetPosts() throws Exception {

    stubMockWireServer();

    List<Post> posts = cut.fetchAllPost();
    assertThat(posts).hasSize(251);
  }

  private void stubMockWireServer() {
    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("0"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-one.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("30"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-two.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("60"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-three.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("90"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-four.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("120"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-five.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("150"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-six.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("180"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-seven.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("210"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-eight.json")
            .withHeader("Content-Type", "application/json")
        )
    );

    mockServer.stubFor(
      WireMock.get(WireMock.urlPathEqualTo("/posts"))
        .withQueryParam("limit", equalTo("30"))
        .withQueryParam("skip", equalTo("240"))
        .willReturn(
          WireMock.aResponse()
            .withBodyFile("dummyjson/get-all-posts-page-final.json")
            .withHeader("Content-Type", "application/json")
        )
    );
  }

}
