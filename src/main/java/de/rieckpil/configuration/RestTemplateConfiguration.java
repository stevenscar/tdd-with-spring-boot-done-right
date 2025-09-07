package de.rieckpil.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  private final String postServiceBaseUrl;

  public RestTemplateConfiguration(
      @Value("${app.post-service-base-url}") String postServiceBaseUrl) {
    this.postServiceBaseUrl = postServiceBaseUrl;
  }

  @Bean
  public RestTemplate postServiceRestTemplate() {
    return new RestTemplateBuilder().rootUri(postServiceBaseUrl).build();
  }
}
