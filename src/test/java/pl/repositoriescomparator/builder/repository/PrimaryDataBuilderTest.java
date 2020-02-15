package pl.repositoriescomparator.builder.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import pl.repositoriescomparator.dto.repository.PrimaryDataDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class PrimaryDataBuilderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PrimaryDataBuilder builder = new PrimaryDataBuilder();

    @Test
    public void testItBuildsDtoWithPrimaryData() throws JsonProcessingException {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("stargazers_count", 1);
        objectNode.put("forks_count", 2);
        objectNode.put("watchers_count", 3);
        String json = objectNode.toString();

        PrimaryDataDto dto = builder.withPrimaryData(json);

        assertThat(dto.getStarsNumber()).isEqualTo(1);
        assertThat(dto.getForksNumber()).isEqualTo(2);
        assertThat(dto.getWatchersNumber()).isEqualTo(3);
    }

    @Test
    public void testItThrowsException_WhenInvalidJsonStringProvided() {
        String invalidJsonString = "foo";

        assertThatExceptionOfType(JsonProcessingException.class)
                .isThrownBy(() -> builder.withPrimaryData(invalidJsonString));
    }
}
