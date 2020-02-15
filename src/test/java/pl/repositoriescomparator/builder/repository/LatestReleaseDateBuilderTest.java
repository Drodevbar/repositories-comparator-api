package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import pl.repositoriescomparator.dto.repository.LatestReleaseDateDto;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LatestReleaseDateBuilderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final LatestReleaseDateBuilder builder = new LatestReleaseDateBuilder();

    @Test
    public void testItBuildsDtoWithLatestReleaseDate() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        String dateString = "2020-01-01T12:12:12Z";
        objectNode.put("published_at", dateString);
        String json = objectNode.toString();

        LatestReleaseDateDto dto = builder.withLatestReleaseDate(json);

        assertThat(dto.getLatestReleaseDate()).isEqualTo(Instant.parse(dateString));
    }

    @Test
    public void testItThrowsException_WhenInvalidJsonStringProvided() {
        String invalidJsonString = "foo";

        assertThatExceptionOfType(JsonProcessingException.class)
                .isThrownBy(() -> builder.withLatestReleaseDate(invalidJsonString));
    }
}
