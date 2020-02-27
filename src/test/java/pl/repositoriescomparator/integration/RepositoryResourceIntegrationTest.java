package pl.repositoriescomparator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.repositoriescomparator.RepositoriesComparatorApplication;

import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = RepositoriesComparatorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties="integration.github.baseuri:http://localhost:9091")
public class RepositoryResourceIntegrationTest {

    private static final Instant NOW = Instant.now();
    private static final String REPOSITORY_OWNER = "foo";
    private static final String REPOSITORY_NAME = "bar";

    @Autowired
    private MockMvc mvc;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9091);

    @Test
    public void testItReturnsRepositoryData_WhenRepositoryFound() throws Exception {
        stubFor(get(urlEqualTo("/" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(createPrimaryRepositoryDataJsonString())));

        stubFor(get(urlEqualTo("/" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME + "/releases/latest"))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(createLatestReleaseDateRepositoryDataJsonString())));

        stubFor(get(urlPathEqualTo("/"))
                .withQueryParam("q", containing("repo:" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())
                        .withBody(createPullRequestsNumberRepositoryDataJsonString())));

        mvc.perform(MockMvcRequestBuilders.get("/api/repository/" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.stars_number", is(1)))
            .andExpect(jsonPath("$.forks_number", is(2)))
            .andExpect(jsonPath("$.watchers_number", is(3)))
            .andExpect(jsonPath("$.merged_pull_requests_number", is(4)))
            .andExpect(jsonPath("$.closed_pull_requests_number", is(4)))
            .andExpect(jsonPath("$.open_pull_requests_number", is(4)))
            .andExpect(jsonPath("$.latest_release_date", is(NOW.toString())));
    }

    @Test
    public void testItReturnsNotFound_WhenRepositoryNotFound() throws Exception {
        stubFor(get(urlEqualTo("/" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        mvc.perform(MockMvcRequestBuilders.get("/api/repository/" + REPOSITORY_OWNER + "/" + REPOSITORY_NAME))
                .andExpect(status().isNotFound());
    }

    private String createPrimaryRepositoryDataJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("stargazers_count", 1);
        objectNode.put("forks_count", 2);
        objectNode.put("watchers_count", 3);

        return objectNode.toString();
    }

    private String createPullRequestsNumberRepositoryDataJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 4);

        return objectNode.toString();
    }

    private String createLatestReleaseDateRepositoryDataJsonString() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("published_at", NOW.toString());

        return objectNode.toString();
    }
}
