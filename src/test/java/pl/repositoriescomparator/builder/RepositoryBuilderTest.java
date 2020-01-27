package pl.repositoriescomparator.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import pl.repositoriescomparator.dto.RepositoryDto;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RepositoryBuilderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RepositoryBuilder repositoryBuilder = new RepositoryBuilder();

    @Test
    public void testItBuildsRepositoryDtoWithPrimaryData() throws JsonProcessingException {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("stargazers_count", 1);
        objectNode.put("forks_count", 2);
        objectNode.put("watchers_count", 3);
        String json = objectNode.toString();

        dto = repositoryBuilder.withPrimaryData(dto, json);

        assertThat(dto.getStarsNumber()).isEqualTo(1);
        assertThat(dto.getForksNumber()).isEqualTo(2);
        assertThat(dto.getWatchersNumber()).isEqualTo(3);
    }

    @Test
    public void testItBuildsRepositoryDtoWithLatestReleaseDate() throws JsonProcessingException {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        String dateString = "2020-01-01T12:12:12Z";
        objectNode.put("published_at", dateString);
        String json = objectNode.toString();

        dto = repositoryBuilder.withLatestReleaseDate(dto, json);

        assertThat(dto.getLatestReleaseDate()).isEqualTo(Instant.parse(dateString));
    }

    @Test
    public void testItBuildsRepositoryDtoWithOpenPullRequestsNumber() throws JsonProcessingException {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        dto = repositoryBuilder.withOpenPullRequestsNumber(dto, json);

        assertThat(dto.getOpenPullRequestsNumber()).isEqualTo(1);
    }

    @Test
    public void testItBuildsRepositoryDtoWithClosedPullRequestsNumber() throws JsonProcessingException {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        dto = repositoryBuilder.withClosedPullRequestsNumber(dto, json);

        assertThat(dto.getClosedPullRequestsNumber()).isEqualTo(1);
    }

    @Test
    public void testItBuildsRepositoryDtoWithMergedPullRequestsNumber() throws JsonProcessingException {
        RepositoryDto dto = new RepositoryDto();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("total_count", 1);
        String json = objectNode.toString();

        dto = repositoryBuilder.withMergedPullRequestsNumber(dto, json);

        assertThat(dto.getMergedPullRequestsNumber()).isEqualTo(1);
    }

    @Test
    public void testItThrowsExceptionWhenInvalidJsonStringProvided() {
        String invalidJsonString = "foo";

        assertThatExceptionOfType(JsonProcessingException.class)
                .isThrownBy(() -> repositoryBuilder.withPrimaryData(new RepositoryDto(), invalidJsonString));
    }
}
